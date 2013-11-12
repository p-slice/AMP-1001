package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

class CommandSolve {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int p, int rank) {

        String stringEquation = "";

        for (int z = 0; z < messageSplit.length; z++)
            stringEquation += messageSplit[z];
        String[] equationSplit = stringEquation.split("((?<=[-?\\d,x])|(?=[-?\\d,x]))");

        if (equationSplit[0].equals("+solvequadratic")){
            List<String> list = new ArrayList<>();
            list.add("+solve");
            list.add("quadratic");
            for (int x = 1; x < equationSplit.length; x++)
                list.add(equationSplit[x]);

            equationSplit = list.toArray(new String[list.size()]);
        }
        int l = equationSplit.length;

        if (p >= rank) {
            if (l == 11 && equationSplit[1].toLowerCase().equals("quadratic")){

                if (!equationSplit[2].equals("0")
                        && equationSplit[3].equals("x")
                        && equationSplit[4].equals("^")
                        && equationSplit[5].equals("2")
                        && equationSplit[6].matches("[+,-]+")
                        && equationSplit[8].equals("x")
                        && equationSplit[9].matches("[+,-]+")){
                    String result = solveQuadratic(equationSplit);
                    bot.sendMessage(chan, result);
                }
                else
                    bot.sendMessage(chan, "Error - equation is not quadratic!");
            }
            else if (l >= 4 && !equationSplit[1].toLowerCase().equals("quadratic")) {
                for (int i = 0; i < equationSplit.length; i ++){
                    if (equationSplit[i].equals("/") && equationSplit[i+1].matches("[-?0]+")){
                        bot.sendMessage(chan, "Error - equation contains division by 0!");
                        return;
                    }
                }
                double result = solveFullEquation(equationSplit);
                bot.sendMessage(chan, "Result: " + String.valueOf(result).replaceAll("\\.0\\b", ""));
            } else
                Command.throwIncorrectParametersError(user, "+solve <equation>");
        } else
            Command.throwNoRankError(user, rank, p);
    }

    private static double solveFullEquation(String[] equation){
        List<Double> numList = new ArrayList<>();
        List<String> opList = new ArrayList<>();

        for (int i = 1; i < equation.length; i++){
            if (equation[i].matches("[+,\\-,*,/,^]+"))
                opList.add(equation[i]);
            else if (equation[i].matches("-?\\d+"))
                numList.add(Double.parseDouble(equation[i]));
        }

        while (numList.size() > 1){
            for (int l = 0; l < opList.size(); l++){
                double first = numList.get(l);
                double second = numList.get(l + 1);

                String op = opList.get(l);

                boolean didEquation = false;

                if (op.equals("^")) {
                    first = Math.pow(first, second);
                    didEquation = true;
                }
                else if (op.equals("/")
                        && !opList.contains("^")) {
                    first /= second;
                    didEquation = true;
                }
                else if (op.equals("*")
                        && !opList.contains("^")) {
                    first *= second;
                    didEquation = true;
                }
                else if (op.equals("+")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/")) {
                    first += second;
                    didEquation = true;
                }
                else if (op.equals("-")
                        && !opList.contains("^")
                        && !opList.contains("*")
                        && !opList.contains("/"))  {
                    first -= second;
                    didEquation = true;
                }

                if (didEquation){
                    numList.remove(l + 1);
                    numList.remove(l);
                    numList.add(l, first);
                    opList.remove(l);
                }
            }
        }
        return numList.get(0);
    }

    private static String solveQuadratic(String[] equation){
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
            result = "Error - situation is impossible!";
        else {
            result1 = (-b + Math.sqrt(part1)) / (2 * a);
            result2 = (-b - Math.sqrt(part1)) / (2 * a);

            String res1 = String.valueOf(result1).replaceAll("\\.0\\b", "");
            String res2 = String.valueOf(result2).replaceAll("\\.0\\b", "");

            System.out.println(res1.split("").length);

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
}
