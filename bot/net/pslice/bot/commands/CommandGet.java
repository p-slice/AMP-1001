package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandGet {

    private static final PircBotX bot = AMP.getBot();

    public static void getCommandList(User user, int l, int p) {

        if (p >= 0) {
            if (l == 1) {
                String[] commandList = BotCommand.getCommandList();
                for (String aCommandList : commandList)
                    bot.sendNotice(user, String.format(aCommandList, BotCommand.getCommandRank(aCommandList.split("[ ]")[0].split("\\+")[1])));
            } else
                bot.sendNotice(user, "Incorrect parameters! Command is '+commands'");
        } else
            Command.throwNoRankError(user, 0, p);
    }

    public static void setCommandInfo(Channel chan, User user, String[] messageSplit, int l, int p){
        if (p >= 10) {
            if (l == 4) {
                switch (messageSplit[1].toLowerCase()){
                    case "rank":
                        boolean a = false;
                        try {
                            Integer.parseInt(messageSplit[3]);
                            a = true;
                        } catch (Exception e){
                            Command.throwImpossibleSettingError(user, "required command rank", messageSplit[3], "String");
                        }
                        if (a){
                            BotCommand.setCommandRank(messageSplit[2].toLowerCase(), messageSplit[3]);
                            bot.sendMessage(chan, "Changed required rank for " + messageSplit[2] + " command to " + messageSplit[3]);
                        }
                        break;
                    default:
                        Command.throwUnknownSettingError(user, messageSplit[0], messageSplit[1]);
                        break;
                }
            } else
                Command.throwIncorrectParametersError(user, "+setcommand <setting> <command> <new setting>");
        } else
            Command.throwNoRankError(user, 10, p);
    }
}
