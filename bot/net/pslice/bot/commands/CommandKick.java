package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class CommandKick {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 2)
                bot.kick(chan, bot.getUser(messageSplit[1]), "Kicked from channel.");
            else if (l > 2) {
                String reason = messageSplit[2];
                for (int i = 3; i < messageSplit.length; i++)
                    reason = reason + " " + messageSplit[i];
                bot.kick(chan, bot.getUser(messageSplit[1]), reason);
            } else
                Command.throwIncorrectParametersError(user, "+kick <user> (reason)");
        } else
            Command.throwNoRankError(user, rank, p);
    }

    public static void languageKick(Channel chan, User user) {
        bot.kick(chan, user, "That sort of language is not allowed in here.");
    }
}
