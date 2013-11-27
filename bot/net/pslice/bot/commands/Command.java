package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import net.pslice.bot.source.BotUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Command {

    static boolean isOverride = false;

    protected static final PircBotX bot = AMP.getBot();

    public static void runCommand(Channel chan, User user, String[] messageSplit) {
        String command = messageSplit[0].toLowerCase();
        int l = messageSplit.length;

        int p;
        if (isOverride) {
            if (!user.getNick().equals("p_slice")) {
                throwGenericError(user, "Bot is currently in override mode and will not accept commands from non-bot Operators.");
                return;
            } else
                p = 100;
        } else if (CommandUser.isOpBased()) {
            if (chan.isOp(user))
                p = 10;
            else if (chan.hasVoice(user))
                p = 7;
            else
                p = 3;
        } else {
            BotUser botUser = new BotUser(user.getNick());
            p = botUser.getRank();
        }

        if (messageSplit[0].equals("+override")) {
            CommandOverride.execute(chan, user, l);
            return;
        }

        int r = Integer.parseInt(BotCommand.getCommandRank(messageSplit[0].split("[\\+]")[1]));

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
            case "+ping":
                CommandGet.pingPong(chan);
                break;
            case "+rename":
                CommandInfo.setNick(user, messageSplit, l, p, r);
                break;
            case "+usertype":
                CommandUser.toggleUserType(chan, user, l, p, r);
                break;
            default:
                throwUnknownCommandError(user, messageSplit[0]);
                break;
        }
    }

    static void throwGenericError(User user, String message) {
        bot.sendNotice(user, message);
    }

    private static void throwUnknownCommandError(User user, String command) {
        bot.sendNotice(user, String.format("The command '%s' is unknown! Use +commands for a list of possible commands.", command));
    }

    static void throwNoRankError(User user, int rank, int p) {
        bot.sendNotice(user, String.format("Insufficient permissions! (Required rank: %d. Your rank: %d)", rank, p));
    }

    static void throwUnknownSettingError(User user, String command, String setting) {
        bot.sendNotice(user, String.format("The setting '%s' is unknown for the command '%s'.", setting, command));
    }

    static void throwUnknownChannelError(User user, String channel) {
        bot.sendNotice(user, String.format("'%s' is not a channel!", channel));
    }

    static void throwImpossibleSettingError(User user, String setting, String arg, String type) {
        bot.sendNotice(user, String.format("Argument '%s' cannot be set as a %s for %s", arg, type, setting));
    }

    static void throwIncorrectParametersError(User user, String params) {
        bot.sendNotice(user, String.format("Incorrect parameters! Command is '%s'!", params));
    }

    static void throwUsedNameError(User user) {
        bot.sendNotice(user, "Error: nick is already in use!");
    }
}
