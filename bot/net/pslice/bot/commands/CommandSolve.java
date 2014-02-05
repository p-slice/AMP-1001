package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class CommandSolve implements Command {

    // The current known operations
    private final static String ops = "[-+*/^()!]";

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command will solve a basic equation and return
     *     the answer
     * Current operations are +, -, *, /, and ^. Brackets and
     *     variables may be added soon
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Make sure there is something to solve
        if (args.length > 0)
        {
            String fullEquation = "";
            for (String arg : args)
                fullEquation += arg;

            List<String> result = new LinkedList<>(Arrays.asList(fullEquation.split("((?<=op)|(?=op))".replace("op", ops))));
            if (result.get(0).equals(" ") || result.get(0).equals(""))
                result.remove(0);

            List<Double> numbers = new ArrayList<>();
            List<String> operations = new ArrayList<>();

            int b, f, s;

            // It may be weird, but the program starts by looking backwards through the equation
            for (b = result.size() - 1; b >= 0; b--)
            {
                numbers.clear();
                operations.clear();

                // Begin solving if the item is a bracket, or the end is reached
                if (result.get(b).equals("(")
                        || b == 0)
                {
                    for (f = b; f < result.size(); f++)
                    {

                        // If a bracketed section is being worked on, end when it hits the closer
                        if (result.get(b).equals("(")
                                && result.get(f).equals(")"))
                        {
                            result.remove(f);
                            break;
                        }

                        // Look for factorials - replace them with an regular number
                        if (f < result.size() - 1
                                && result.get(f).matches("\\d+(\\.0)?")
                                && result.get(f + 1).equals("!"))
                        {
                            int i = Integer.parseInt(result.get(f).replaceAll("\\.0\\b", ""));
                            int r = 1;
                            while (i > 0)
                            {
                                r *= i;
                                i--;
                            }
                            result.set(f, String.valueOf(r));
                            result.remove(f + 1);
                        }

                        // If a factorial isn't working on a number, it's in the wrong place
                        if (result.get(f).equals("!"))
                        {
                            CommandManager.throwGenericError(bot, sender, "Error: Misplaced factorial!");
                            return;
                        }

                        // Look for '-' signs - could be either 'minus' or 'negative'
                        if (result.get(f).equals("-")
                                && result.get(f + 1).matches("\\d*\\.?\\d*E?(-?\\d)*"))
                        {
                            // If it is the first item or the previous item is an operation, assume 'negative'
                            if (f == b || result.get(f - 1).matches(ops))
                            {
                                numbers.add(Double.parseDouble("-" + result.get(f + 1)));
                                f++;
                            }

                            // Otherwise it's probably 'minus'
                            else
                                operations.add("-");
                        }

                        // If the item is a number, add it
                        else if (result.get(f).matches("(-?\\d)*\\.?\\d*E?(-?\\d)*"))
                            numbers.add(Double.parseDouble(result.get(f)));

                            // If the item is an operator, add it
                        else if (result.get(f).matches(ops))
                        {
                            // Check that no division by 0 occurs
                            if (f < result.size()
                                    && result.get(f).equals("/")
                                    && result.get(f + 1).matches("(-?0)*\\.?0*"))
                            {
                                CommandManager.throwGenericError(bot, sender, "Cannot divide by 0!");
                                return;
                            }
                            if (!result.get(f).matches("[()]"))
                                operations.add(result.get(f));
                        }

                        // If the item didn't get put in one of the lists, it shouldn't be in the equation
                        else
                        {
                            CommandManager.throwGenericError(bot, sender, "Your equation contains an unresolved item: " + result.get(f));
                            return;
                        }
                    }

                    // The number of operators should always be one less than the number of numbers - throw an error if it isn't
                    if (operations.size() + 1 != numbers.size())
                    {
                        CommandManager.throwGenericError(bot, sender, "The number of operations does not match the number of numbers");
                        return;
                    }

                    String current = String.valueOf(getResult(numbers, operations));

                    // Remove the old items from the bracket and replace them with their result
                    for (s = b + 1; s < f; s++)
                        result.remove(b + 1);
                    result.add(b + 1, current);

                    // If the item after the bracketed section is not an operation, insert a multiplication
                    if (b < result.size() - 2
                            && result.get(b).equals("(")
                            && !result.get(b + 2).matches(ops))
                        result.add(b + 2, "*");

                        // If the item before the bracketed section is not an operation, insert a multiplication
                    else if (b > 0
                            && result.get(b).equals("(")
                            && (!result.get(b - 1).matches(ops)
                            || result.get(b - 1).equals(")")))
                        result.set(b, "*");

                        // Otherwise, simply remove the bracket
                    else
                        result.remove(b);
                }
            }

            String finalResult = result.get(0);

            bot.sendMessage(channel, "Result: " + finalResult.replaceAll("\\.0\\b", ""));
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }





    /**
     * ===========================================
     * Static method to solve an equation
     *
     * @param numList: All the numbers in the equation
     * @param opList: All the operations in the equation
     * @return The first number in the list once no more operations
     *           can be done
     * This method assumes that all the numbers and operations
     *     are in order
     * ===========================================
     */

    private static double getResult(List<Double> numList, List<String> opList) {

        // If there are no numbers in the list, put 0 in it
        if (numList.size() == 0)
            numList.add(0D);

        // So long as there are more numbers and operators, keep solving
        while (numList.size() > 1
                && opList.size() > 0)
        {

            // Run through each number and operator, following order of operations
            for (int l = 0; l < opList.size(); l++)
            {
                double first = numList.get(l);
                double second = numList.get(l + 1);

                String op = opList.get(l);

                boolean didEquation = false;

                // Any exponents are solved first
                if (op.equals("^"))
                {
                    first = Math.pow(first, second);
                    didEquation = true;
                }

                // Division is done if no exponents remain
                else if (op.equals("/")
                        && !opList.contains("^"))
                {
                    first /= second;
                    didEquation = true;
                }

                // Multiplication is done if no exponents remain
                else if (op.equals("*")
                        && !opList.contains("^"))
                {
                    first *= second;
                    didEquation = true;
                }

                // Addition is done if no exponents, multipliers or divisors remain
                else if (op.equals("+")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/"))
                {
                    first += second;
                    didEquation = true;
                }

                // Subtraction is done if no exponents, multipliers or divisors remain
                else if (op.equals("-")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/"))
                {
                    first -= second;
                    didEquation = true;
                }

                // If an equation was done, update the lists
                if (didEquation) {
                    numList.remove(l + 1);
                    numList.remove(l);
                    numList.add(l, first);
                    opList.remove(l);
                    break;
                }
            }
        }
        return numList.get(0);
    }
}
