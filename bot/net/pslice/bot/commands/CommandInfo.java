package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandInfo {

    private static final PircBotX bot = AMP.getBot();

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
                Command.throwIncorrectParametersError(user, "+info <setting>");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
