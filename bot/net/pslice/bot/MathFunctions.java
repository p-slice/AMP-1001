package net.pslice.bot;

import net.pslice.bot.managers.CommandManager;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class MathFunctions {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    public final static String

            // The current known operations
            ops = "[-+*/^()!a-z]";





    /*
     * ===========================================
     * Initializer:
     *
     * Private to ensure no pointless instances are created
     * ===========================================
     */

    private MathFunctions(){}





    /**
     * ===========================================
     * Static method to split an equation into operators and numbers:
     *
     * @param equation: The equation being split
     * @return The split equation
     * ===========================================
     */

    public static List<String> splitEquation(String equation)
    {
        List<String> result = new LinkedList<>(Arrays.asList(equation.split("((?<=op)|(?=op))".replace("op", ops))));
        for (int i = 0; i < result.size(); i++)
            if (result.get(i).equals(" ") || result.get(i).equals(""))
                result.remove(i);

        return result;
    }





    /**
     * ===========================================
     * Static method to replace one item in an equation with another:
     *
     * @param equation: The equation containing the item being replaced
     * @param oldItem: The item being replaced
     * @param newItem: The replacement item
     * @return The equation with the new replacements
     * This method is mainly used to replace variables
     *     with actual values
     * It may also be used to replace constant words
     *     (Eg. pi) with the values they represent
     * Since variables are often expressed directly beside
     *     values they may be multiplied by, '*' symbols are
     *     automatically added, then removed if found to be
     *     unneeded
     * ===========================================
     */

    public static String replaceValues(String equation, String oldItem, String newItem)
    {
        equation = equation.replaceAll(oldItem, "*" + newItem + "*");

        List<String> equationSplit = new LinkedList<>(Arrays.asList(equation.split("(?!^)")));

        // Remove possible extra '*'s from the start and end
        for (int i = 0; i < equationSplit.size(); i++)
            if (equationSplit.get(i).equals("*")
                    && (i == 0
                    || i == equationSplit.size() - 1))
                equationSplit.remove(i);

        // Remove possible extra '*'s that may have been added after another operation
        for (int i = 1; i < equationSplit.size(); i++)
            if (equationSplit.get(i).equals("*")
                    && (equationSplit.get(i - 1).matches(ops)
                    && !equationSplit.get(i - 1).equals("!")))
                equationSplit.remove(i);

        // Remove possible extra '*'s that may have been added before another operation
        for (int i = 0; i < equationSplit.size() - 1; i++)
            if (equationSplit.get(i).equals("*")
                    && (equationSplit.get(i + 1).matches(ops)
                    && !equationSplit.get(i + 1).equals("!")))
                equationSplit.remove(i);

        // Save the equation without the extra multiplication
        equation = "";
        for (String s : equationSplit)
            equation += s;

        return equation;
    }





    /**
     * ===========================================
     * Static method to clean up a section of the equation:
     *
     * @param equation: The equation being cleaned
     * @param b: The start position of the section being cleaned
     * @param f: The end position of the section being cleaned
     * @param current: The replacement of the section
     * This method is used after a bracketed section is
     *     solved
     * It is also used after a function is replaced
     * ===========================================
     */

    private static void finalize(List<String> equation, int b, int f, String current)
    {
        int s;

        // Remove the old items from the bracket and replace them with their result
        for (s = b + 1; s < f; s++)
            equation.remove(b + 1);
        equation.add(b + 1, current);

        // If the item after the bracketed section is not an operation, insert a multiplication
        if (b < equation.size() - 2
                && equation.get(b).equals("(")
                && !equation.get(b + 2).matches(ops))
            equation.add(b + 2, "*");

        // If the item before the bracketed section is not an operation, insert a multiplication
        if (b > 0
                && equation.get(b).equals("(")
                && (!equation.get(b - 1).matches(ops)
                || equation.get(b - 1).equals(")")))
            equation.set(b, "*");

        // Otherwise, simply remove the bracket
        else
            equation.remove(b);
    }





    /**
     * ===========================================
     * Static method to simplify an equation until only one item remains:
     *
     * @param bot: The bot solving the equation (required for throwing errors)
     * @param sender: The user requesting the simplification (required for throwing errors)
     * @param equation: The equation being simplified
     * @return The last remaining item in the equation
     *         Null if an error is thrown
     * This method will take any equation given and try to
     *     simplify it as much as possible
     * Functions will also be simplified, but must be expressed
     *     with a number with which to replace 'x'. They can
     *     also accept equations, other functions, or
     *     combinations of all
     * The current supported operations are: +, -, *, /, ^, !
     *     and brackets, with no limit on bracket number.
     *     Brackets do not have to be closed at the end,
     *     except when separating other items in the
     *     equation
     * ===========================================
     */

    public static String simplifyEquation(AmpBot bot, User sender, List<String> equation)
    {
        List<Double> numbers = new ArrayList<>();
        List<String> operations = new ArrayList<>();

        int b, f;

        // Make sure the process keeps happening if the equation is still greater than one
        while (equation.size() > 1)
        {
            // It may be weird, but the program starts by looking backwards through the equation
            for (b = equation.size() - 1; b >= 0; b--)
            {
                numbers.clear();
                operations.clear();

                // Look for variables
                if (equation.get(b).matches("[a-z]"))
                {
                    // Check if the letter is part of a function
                    if (b < equation.size() - 2
                            && equation.get(b + 1).equals("("))
                    {
                        String functionEquation = "";
                        for (f = b + 1; f < equation.size(); f++)
                        {
                            if (equation.get(f).equals(")"))
                            {
                                equation.remove(f);
                                break;
                            }
                            else
                                functionEquation += equation.get(f);
                        }

                        // Simplify the arguments in the function, just in case
                        functionEquation = simplifyEquation(bot, sender, splitEquation(functionEquation));

                        String function = bot.getPropertiesManager().getFunction(equation.get(b) + "(x)");
                        // Check if the function actually exists, throw an error if it doesn't
                        if (function.equals(""))
                        {
                            CommandManager.throwGenericError(bot, sender, "No function is currently saved in " + equation.get(b) + "(x)");
                            return null;
                        }

                        function = simplifyEquation(bot, sender, splitEquation(replaceValues(function, "x", functionEquation)));

                        equation.remove(b);
                        finalize(equation, b, f - 1, function);
                    }

                    // Otherwise throw an error - nothing can be done with variables currently
                    else
                    {
                        CommandManager.throwGenericError(bot, sender, "Incorrectly placed variable: " + equation.get(b));
                        return null;
                    }
                }

                // Begin solving if the item is a bracket, or the end is reached
                else if ( b == 0
                        || (equation.get(b).equals("(")
                        && !equation.get(b - 1).matches("[a-z]")))
                {
                    for (f = b; f < equation.size(); f++)
                    {
                        // If a bracketed section is being worked on, end when it hits the closer
                        if (equation.get(b).equals("(")
                                && equation.get(f).equals(")"))
                        {
                            equation.remove(f);
                            break;
                        }

                        // Look for factorials - replace them with an regular number
                        if (f < equation.size() - 1
                                && equation.get(f).matches("\\d+(\\.0)?")
                                && equation.get(f + 1).equals("!"))
                        {
                            int i = Integer.parseInt(equation.get(f).replaceAll("\\.0\\b", ""));
                            int r = 1;
                            while (i > 0)
                            {
                                r *= i;
                                i--;
                            }
                            equation.set(f, String.valueOf(r));
                            equation.remove(f + 1);
                        }

                        // If a factorial isn't working on a number, it's in the wrong place
                        if (equation.get(f).equals("!"))
                        {
                            CommandManager.throwGenericError(bot, sender, "Error: Misplaced factorial!");
                            return null;
                        }

                        // Look for '-' signs - could be either 'minus' or 'negative'
                        if (equation.get(f).equals("-")
                                && equation.get(f + 1).matches("\\d*\\.?\\d*E?(-?\\d)*"))
                        {
                            // If it is the first item or the previous item is an operation, assume 'negative'
                            if (f == b || equation.get(f - 1).matches(ops))
                            {
                                numbers.add(Double.parseDouble("-" + equation.get(f + 1)));
                                f++;
                            }

                            // Otherwise it's probably 'minus'
                            else
                                operations.add("-");
                        }

                        // If the item is a number, add it
                        else if (equation.get(f).matches("(-?\\d)*\\.?\\d*E?(-?\\d)*"))
                            numbers.add(Double.parseDouble(equation.get(f)));

                            // If the item is an operator, add it
                        else if (equation.get(f).matches(ops))
                        {
                            // Check that no division by 0 occurs
                            if (f < equation.size()
                                    && equation.get(f).equals("/")
                                    && equation.get(f + 1).matches("(-?0)*\\.?0*"))
                            {
                                CommandManager.throwGenericError(bot, sender, "Cannot divide by 0!");
                                return null;
                            }
                            if (!equation.get(f).matches("[()]"))
                                operations.add(equation.get(f));
                        }

                        // If the item didn't get put in one of the lists, it shouldn't be in the equation
                        else
                        {
                            CommandManager.throwGenericError(bot, sender, "Your equation contains an unresolved item: " + equation.get(f));
                            return null;
                        }
                    }

                    // The number of operators should always be one less than the number of numbers - throw an error if it isn't
                    if (operations.size() + 1 != numbers.size())
                    {
                        CommandManager.throwGenericError(bot, sender, "The number of operations does not match the number of numbers");
                        return null;
                    }

                    String current = String.valueOf(basicResult(numbers, operations));

                    finalize(equation, b, f, current);
                }
            }
        }
        return equation.get(0);
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

    private static double basicResult(List<Double> numList, List<String> opList) {

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
                    first = java.lang.Math.pow(first, second);
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
