package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import net.pslice.bot.source.BotUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Command {

    private static final PircBotX bot = AMP.getBot();

    public static void runCommand(Channel chan, User user, String[] messageSplit) {
        String command = messageSplit[0].toLowerCase();
        int l = messageSplit.length;

        BotUser botUser = new BotUser(user.getNick());
        int p = botUser.getRank();

        int r = Integer.parseInt(BotCommand.getCommandRank(messageSplit[0]));

        switch (command) {
            case "+quit":
                CommandQuit.execute(user, l, p, r);
                break;
            case "+say":
                CommandSay.execute(chan, user, messageSplit, l, p, r);
                break;
            case "+join":
                CommandJoin.execute(user, messageSplit, l, p, r);
                break;
            case "+leave":
                CommandLeave.execute(chan, user, messageSplit, l, p, r);
                break;
            case "+kick":
                CommandKick.execute(chan, user, messageSplit, l, p, r);
                break;
            case "+server":
                CommandServer.execute(chan, user, messageSplit, l, p, r);
                break;
            case "+twerk":
                CommandTwerk.execute(chan, user, l, p, r);
                break;
            case "+user":
                CommandUser.getInfo(chan, user, messageSplit, l, p, r);
                break;
            case "+setuser":
                CommandUser.setInfo(chan, user, messageSplit, l, p, r);
                break;
            case "+adduser":
                CommandUser.addUser(chan, user, messageSplit, l, p, r);
                break;
            case "+commands":
                CommandGet.getCommandList(user, l, p);
                break;
            case "+info":
                CommandInfo.execute(chan, user, messageSplit, l, p, r);
                break;
            case "+verbose":
                CommandVerbose.execute(user, messageSplit, l, p, r);
                break;
            case "+solve":
                CommandSolve.execute(chan, user, messageSplit, p, r);
                break;
            case "+setcommand":
                CommandGet.setCommandInfo(chan, user, messageSplit, l, p);
                break;
            default:
                throwUnknownCommandError(user, messageSplit[0]);
                break;
        }
    }

    private static void throwUnknownCommandError(User user, String command){
        bot.sendNotice(user, String.format("The command '%s' is unknown! Use +commands for a list of possible commands.", command));
    }

    static void throwNoRankError(User user, int rank, int p){
        bot.sendNotice(user, String.format("Insufficient permissions! (Required rank: %d. Your rank: %d)", rank, p));
    }

    static void throwUnknownSettingError(User user, String command, String setting){
        bot.sendNotice(user, String.format("The setting '%s' is unknown for the command '%s'.", setting, command));
    }

    static void throwUnknownChannelError(User user, String channel){
        bot.sendNotice(user, String.format("'%s' is not a channel!", channel));
    }

    static void throwImpossibleSettingError(User user, String setting, String arg, String type){
        bot.sendNotice(user, String.format("Argument '%s' cannot be set as a %s for %s", arg, type, setting));
    }

    static void throwIncorrectParametersError(User user, String params){
        bot.sendNotice(user, String.format("Incorrect parameters! Command is '%s'!", params));
    }
}
