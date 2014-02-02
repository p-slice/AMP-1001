package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

public final class CommandSolve implements Command {

    // The current known operations
    private final static String ops = "[-+*/^]";

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
            List<Double> numbers = new ArrayList<>();
            List<String> operations = new ArrayList<>();

            String fullEquation = "";
            for (String arg : args)
                fullEquation += arg;

            String[] equationSplit = fullEquation.split("((?<=op)|(?=op))".replace("op", ops));

            // Split the equation into numbers and operations
            for (int z = 0; z < equationSplit.length; z++)
            {
                // Look for '-' signs - could be either 'minus' or 'negative'
                if (equationSplit[z].equals("-")
                        && equationSplit[z + 1].matches("\\d+"))
                {
                    // If it is the first item or the previous item is an operation, assume 'negative'
                    if (z == 1 || equationSplit[z - 1].matches(ops))
                    {
                        numbers.add(Double.parseDouble("-" + equationSplit[z + 1]));
                        z++;
                    }

                    // Otherwise it's probably 'minus'
                    else
                        operations.add("-");
                }

                // If the item is a number, add it
                else if (equationSplit[z].matches("\\d+"))
                    numbers.add(Double.parseDouble(equationSplit[z]));

                // If the item is an operator, add it
                else if (equationSplit[z].matches(ops))
                {
                    // Check that no division by 0 occurs
                    if (z < equationSplit.length
                            && equationSplit[z].equals("/")
                            && equationSplit[z + 1].matches("[-?0]")) {
                        bot.sendMessage(channel, "Error: Cannot divide by 0!");
                        return;
                    }
                    operations.add(equationSplit[z]);
                }

                // Throw an error if the item is something other than a number or operator
                else
                {
                    CommandManager.throwGenericError(bot, sender, String.format("Error: Your equation contains an unknown item (%s)", equationSplit[z]));
                    return;
                }
            }

            String finalEquation = "";
            for (int a = 0; a < numbers.size(); a++)
            {
                if (a <= numbers.size())
                    finalEquation += numbers.get(a) + " ";
                if (a < operations.size())
                    finalEquation += operations.get(a) + " ";
            }

            bot.sendMessage(channel, "Interpreted as: " + finalEquation.replaceAll("\\.0\\b", ""));

            // Solve the equation
            String result = String.valueOf(getResult(numbers, operations));

            // Shorten decimal answers to prevent spam
            if (result.contains(".")) {
                String[] resSplit = result.split("[.]");
                if (resSplit[1].length() > 6)
                    result = resSplit[0] + "." + resSplit[1].substring(0, 6);
            }

            bot.sendMessage(channel, "Result: " + String.valueOf(result).replaceAll("\\.0\\b", ""));
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
                }
            }
        }
        return numList.get(0);
    }
}
