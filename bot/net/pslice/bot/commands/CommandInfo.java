package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandInfo extends Command {

    public static void setNick(User user, String[] messageSplit, int l, int p, int rank) {
        if (p >= rank) {
            if (l == 2) {
                if (bot.userExists(messageSplit[1]))
                    throwUsedNameError(user);
                else
                    bot.setName(messageSplit[1]);
            } else
                throwIncorrectParametersError(user, "+rename <nick>");
        } else
            throwNoRankError(user, rank, p);
    }

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 2) {
                switch (messageSplit[1].toLowerCase()) {
                    case "nick":
                        bot.sendMessage(chan, "Nick: " + bot.getNick());
                        break;
                    case "login":
                        bot.sendMessage(chan, "Login: " + bot.getLogin());
                        break;
                    case "password":
                        bot.sendMessage(chan, "Password: Ahahahaha nope.");
                        break;
                    case "network":
                        bot.sendMessage(chan, "Network: " + bot.getServer());
                        break;
                    default:
                        bot.sendNotice(user, "Unrecognized info commands");
                        break;
                }
            } else
                throwIncorrectParametersError(user, "+info <setting>");
        } else
            throwNoRankError(user, rank, p);
    }
}
