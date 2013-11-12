package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandJoin {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 2)
                if (messageSplit[1].startsWith("#"))
                    bot.joinChannel(messageSplit[1]);
                else
                    Command.throwUnknownChannelError(user, messageSplit[1]);
            else
                Command.throwIncorrectParametersError(user, "+join <channel>");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
