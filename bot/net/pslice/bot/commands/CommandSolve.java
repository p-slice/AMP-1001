package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

public final class CommandSolve implements Command {

    private final static String ops = "[-+*/^]";

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length > 0)
        {
            List<Double> numbers = new ArrayList<>();
            List<String> operations = new ArrayList<>();

            String fullEquation = "";
            for (String arg : args)
                fullEquation += arg;

            String[] equationSplit = fullEquation.split("((?<=op)|(?=op))".replace("op", ops));

            for (int z = 0; z < equationSplit.length; z++)
            {
                if (equationSplit[z].equals("-")
                        && equationSplit[z + 1].matches("\\d+"))
                {
                    if (z > 0 && equationSplit[z - 1].matches("[)]"))
                        operations.add(equationSplit[z]);
                    else if (z == 1 || equationSplit[z - 1].matches(ops))
                    {
                        numbers.add(Double.parseDouble("-" + equationSplit[z + 1]));
                        z++;
                    }
                    else
                        operations.add("-");
                }
                else if (equationSplit[z].matches("\\d+"))
                    numbers.add(Double.parseDouble(equationSplit[z]));
                else if (equationSplit[z].matches(ops))
                {
                    if (z < equationSplit.length
                            && equationSplit[z].equals("/")
                            && equationSplit[z + 1].matches("[-?0]")) {
                        bot.sendMessage(channel, "Error: Cannot divide by 0!");
                        return;
                    }
                    operations.add(equationSplit[z]);
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

            String result = String.valueOf(getResult(numbers, operations));

            if (result.contains(".")) {
                String[] resSplit = result.split("[.]");
                if (resSplit[1].length() > 6)
                    result = resSplit[0] + "." + resSplit[1].substring(0, 6);
            }

            bot.sendMessage(channel, "Result: " + String.valueOf(result).replaceAll("\\.0\\b", ""));
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }

    private static double getResult(List<Double> numList, List<String> opList) {
        if (numList.size() == 0)
            numList.add((double) 1);
        while (numList.size() > 1) {
            for (int l = 0; l < opList.size(); l++) {
                double first = numList.get(l);
                double second = numList.get(l + 1);

                String op = opList.get(l);

                boolean didEquation = false;

                if (op.equals("^")) {
                    first = Math.pow(first, second);
                    didEquation = true;
                } else if (op.equals("/")
                        && !opList.contains("^")) {
                    first /= second;
                    didEquation = true;
                } else if (op.equals("*")
                        && !opList.contains("^")) {
                    first *= second;
                    didEquation = true;
                } else if (op.equals("+")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/")) {
                    first += second;
                    didEquation = true;
                } else if (op.equals("-")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/")) {
                    first -= second;
                    didEquation = true;
                }

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
