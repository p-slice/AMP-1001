package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandKick extends Command {

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (chan.isOp(bot.getUserBot())) {
                if (l == 2)
                    bot.kick(chan, bot.getUser(messageSplit[1]), "Kicked from channel.");
                else if (l > 2) {
                    String reason = messageSplit[2];
                    for (int i = 3; i < messageSplit.length; i++)
                        reason = reason + " " + messageSplit[i];
                    bot.kick(chan, bot.getUser(messageSplit[1]), reason);
                } else
                    throwIncorrectParametersError(user, "+kick <user> (reason)");
            } else
                throwGenericError(user, "Error: I don't have sufficient permissions to do that!");
        } else
            throwNoRankError(user, rank, p);
    }
}
