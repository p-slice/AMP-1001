package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CommandSolve {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int p, int rank) {

        String[] equationSplit;

        List<String> list = new ArrayList<>();

        if (messageSplit[messageSplit.length - 1].equals("+s"))
            list.addAll(Arrays.asList(messageSplit).subList(0, messageSplit.length - 1));
        else {
            list.add("+solve");
            int x = 1;
            if (messageSplit[1].equals("quadratic")) {
                x = 2;
                list.add("quadratic");
            }
            String equation = "";
            for (int z = x; z < messageSplit.length; z++) {
                equation += messageSplit[z];
            }
            equationSplit = equation.split("((?<=[+,\\-,*,/,^,a-z,A-Z,(,)])|(?=[+,\\-,*,/,^,a-z,A_Z,(,)]))");
            Collections.addAll(list, equationSplit);
            for (int r = 0; r < list.size(); r++){
                if (list.get(r).equals(""))
                    list.remove(r);
            }
        }
        equationSplit = list.toArray(new String[list.size()]);

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
                for (int i = 0; i < equationSplit.length; i++) {
                    if (equationSplit[i].equals("/") && equationSplit[i + 1].matches("[-?0]+")) {
                        bot.sendMessage(chan, "Error: equation contains division by 0!");
                        return;
                    }
                }
                String result = solveFullEquation(equationSplit);
                bot.sendMessage(chan, result);
            } else
                Command.throwIncorrectParametersError(user, "+solve (quadratic) <equation> (+s)");
        } else
            Command.throwNoRankError(user, rank, p);
    }

    private static String solveFullEquation(String[] equation) {

        List<Double> brNumList = new ArrayList<>();
        List<String>  brOpList = new ArrayList<>();

        List<Double> numList = new ArrayList<>();
        List<String> opList = new ArrayList<>();

        for (int i = 1; i < equation.length; i++) {
            if (equation[i].equals("(")){
                int f = 0;
                for (int q = i; q < equation.length; q++){
                    if (equation[q].equals("(") && q != i)
                        return "Error: cannot compute stacked brackets at this time!";
                    else if (equation[q].matches("[+,\\-,*,/,^]+"))
                        brOpList.add(equation[q]);
                    else if (equation[q].matches("-?\\d+"))
                        brNumList.add(Double.parseDouble(equation[q]));
                    else if (equation[q].equals(")")){
                        f = q;
                        break;
                    }
                }
                double bracket = getResult(brNumList, brOpList);

                numList.add(bracket);
                if (!equation[i-1].matches("[+,\\-,*,/,^,+solve,)]+"))
                    opList.add("*");
                if (f+1 != equation.length){
                    if (!equation[f+1].matches("[+,\\-,*,/,^,+s]+"))
                        opList.add("*");
                }
                brNumList.clear();
                brOpList.clear();
                i = f;
            }
            if (equation[i].matches("[+,\\-,*,/,^]+"))
                opList.add(equation[i]);
            else if (equation[i].matches("-?\\d+"))
                numList.add(Double.parseDouble(equation[i]));
            else if (equation[i].matches("-?\\[a-z,A-Z]+"))
                return "Error: cannot currently work with variables!";
        }

        double result = getResult(numList, opList);

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

            if (res1.split("").length >= 6)
                res1 = res1.substring(0, 6);
            if (res2.split("").length >= 6)
                res2 = res2.substring(0, 6);

            if (res1.equals(res2))
                result = String.format("Result: x = {%s}", res1);
            else
                result = String.format("Result: x = {%s, %s}", res1, res2);
        }

        return result;
    }

    private static double getResult(List<Double> numList, List<String> opList){
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
