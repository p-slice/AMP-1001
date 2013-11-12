package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandQuit {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(User user, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 1)
                bot.quitServer("Disconnecting");
            else
                Command.throwIncorrectParametersError(user, "+quit");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
