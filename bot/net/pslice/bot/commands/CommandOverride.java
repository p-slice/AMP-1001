package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandOverride extends Command {

    public static void execute(Channel chan, User user, int l) {

        if (user.getNick().equals("p_slice")
                && user.isVerified()) {
            if (l == 1) {
                isOverride = !isOverride;
                if (isOverride)
                    bot.sendMessage(chan, "Now running in override mode. Access denied to everyone except bot Operators.");
                else
                    bot.sendMessage(chan, "Override mode disabled.");
            } else
                throwIncorrectParametersError(user, "+override");
        } else
            throwGenericError(user, "Error: You do not have permission to do that.");
    }
}
