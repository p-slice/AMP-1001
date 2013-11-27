package net.pslice.bot.commands;

import net.pslice.bot.data.MessageData;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CommandSolve extends Command {

    public static void execute(Channel chan, User user, String[] messageSplit, int p, int rank) {
        String ops = "[-+*/^() x]";

        List<String> parts = new ArrayList<>();

        String[] equationSplit;

        if (messageSplit[messageSplit.length - 1].equals("+s"))
            parts.addAll(Arrays.asList(messageSplit).subList(0, messageSplit.length - 1));
        else {
            String equation = MessageData.getMessage();

            if (equation.contains("(")
                    && !equation.contains(")"))
                equation += ")";

            int start = 7;
            parts.add(messageSplit[0]);
            if (messageSplit[1].toLowerCase().equals("quadratic")) {
                parts.add(messageSplit[1]);
                start = 16;
            }
            equationSplit = equation.substring(start).split("((?<=op)|(?=op))".replace("op", ops));
            for (int z = 0; z < equationSplit.length; z++) {
                if (equationSplit[z].equals("-")
                        && equationSplit[z + 1].matches("[\\d]+")) {
                    if (equationSplit[z - 1].matches("[)]"))
                        parts.add(equationSplit[z]);
                    else if (z == 1) {
                        parts.add("-" + equationSplit[z + 1]);
                        z++;
                    } else if (equationSplit[z - 1].matches(ops)) {
                        parts.add("-" + equationSplit[z + 1]);
                        z++;
                    }
                } else
                    parts.add(equationSplit[z]);
            }

            for (int r = 0; r < parts.size(); r++) {
                String part = parts.get(r);
                if (part.equals("")
                        || part.equals(" "))
                    parts.remove(r);
            }
        }
        equationSplit = parts.toArray(new String[parts.size()]);

        int l = equationSplit.length;

        if (p >= rank) {
            if (l == 11 && equationSplit[1].toLowerCase().equals("quadratic")) {
                if (!equationSplit[2].equals("0")
                        && equationSplit[3].equals("x")
                        && equationSplit[4].equals("^")
                        && equationSplit[5].equals("2")
                        && equationSplit[6].matches("[+,-]+")
                        && equationSplit[8].equals("x")
                        && equationSplit[9].matches("[+,-]+")) {
                    String result = solveQuadratic(equationSplit);
                    bot.sendMessage(chan, result);
                } else
                    bot.sendMessage(chan, "Error: equation is not quadratic!");
            } else if (l >= 4 && !equationSplit[1].toLowerCase().equals("quadratic")) {
                String eq = "";
                for (int i = 1; i < equationSplit.length; i++) {
                    eq += equationSplit[i] + " ";
                    if (equationSplit[i].equals("/") && equationSplit[i + 1].matches("[-?0]+")) {
                        bot.sendMessage(chan, "Error: equation contains division by 0!");
                        return;
                    }
                }
                String result = solveFullEquation(equationSplit);
                bot.sendMessage(chan, "Interpreted equation as: " + eq);
                bot.sendMessage(chan, result);
            } else
                throwIncorrectParametersError(user, "+solve (quadratic) <equation> (+s)");
        } else
            throwNoRankError(user, rank, p);
    }

    private static String solveFullEquation(String[] equation) {

        List<Double> brNumList = new ArrayList<>();
        List<String> brOpList = new ArrayList<>();

        List<Double> numList = new ArrayList<>();
        List<String> opList = new ArrayList<>();

        for (int i = 1; i < equation.length; i++) {
            if (equation[i].equals("(")) {
                int f = 0;
                for (int q = i; q < equation.length; q++) {
                    if (equation[q].equals("(") && q != i)
                        return "Error: cannot compute stacked brackets at this time!";
                    else if (equation[q].matches("[-+*/^]+"))
                        brOpList.add(equation[q]);
                    else if (equation[q].matches("-?\\d+"))
                        brNumList.add(Double.parseDouble(equation[q]));
                    else if (equation[q].equals(")")) {
                        f = q;
                        break;
                    }
                }
                double bracket = getResult(brNumList, brOpList);

                numList.add(bracket);
                if (!equation[i - 1].matches("[-+*/^,+solve]+"))
                    opList.add("*");
                if (f + 1 != equation.length) {
                    if (!equation[f + 1].matches("[-+*/^,+s,(]+"))
                        opList.add("*");
                }
                brNumList.clear();
                brOpList.clear();

                i = f;
            }
            if (equation[i].matches("[-+*/^]+")
                    && !equation[i - 1].matches("[-+*/^]+"))
                opList.add(equation[i]);
            else if (equation[i].matches("-?\\d+"))
                numList.add(Double.parseDouble(equation[i]));
            else if (equation[i].matches("-?\\[a-z,A-Z]+"))
                return "Error: cannot currently work with variables!";
        }

        String result = String.valueOf(getResult(numList, opList)).replaceAll("\\.0\\b", "");

        if (result.contains(".")) {
            String[] resSplit = result.split("[.]");
            if (resSplit[1].length() > 6)
                result = resSplit[0] + "." + resSplit[1].substring(0, 6);
        }

        return "Result: " + String.valueOf(result).replaceAll("\\.0\\b", "");
    }

    private static String solveQuadratic(String[] equation) {
        String result;

        double a = Double.parseDouble(equation[2]);
        double b = Double.parseDouble(equation[7]);
        double c = Double.parseDouble(equation[10]);

        if (equation[6].equals("-"))
            b = -b;
        if (equation[9].equals("-"))
            c = -c;

        double result1;
        double result2;

        double part1 = Math.pow(b, 2) - (4 * a * c);
        if (part1 < 0)
            result = "Error: situation is impossible!";
        else {
            result1 = (-b + Math.sqrt(part1)) / (2 * a);
            result2 = (-b - Math.sqrt(part1)) / (2 * a);

            String res1 = String.valueOf(result1).replaceAll("\\.0\\b", "");
            String res2 = String.valueOf(result2).replaceAll("\\.0\\b", "");

            if (res1.contains(".")) {
                String[] resSplit1 = res1.split(".");
                if (resSplit1[1].length() > 6)
                    res1 = resSplit1[0] + "." + resSplit1[1].substring(0, 6);
            }
            if (res2.contains(".")) {
                String[] resSplit2 = res2.split(".");
                if (resSplit2[1].length() > 6)
                    res1 = resSplit2[0] + "." + resSplit2[1].substring(0, 6);
            }

            if (res1.equals(res2))
                result = String.format("Result: x = {%s}", res1);
            else
                result = String.format("Result: x = {%s, %s}", res1, res2);
        }

        return result;
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
