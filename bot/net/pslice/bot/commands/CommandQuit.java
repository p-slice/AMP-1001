package net.pslice.bot.commands;

import org.pircbotx.User;

class CommandQuit extends Command {

    public static void execute(User user, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 1)
                bot.quitServer("Disconnecting");
            else
                throwIncorrectParametersError(user, "+quit");
        } else
            throwNoRankError(user, rank, p);
    }
}
