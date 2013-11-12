package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandLeave {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 1) {
                if (chan.getName().equals("#p_slice"))
                    bot.sendNotice(user, "No, I refuse to.");
                else
                    bot.partChannel(chan);
            } else if (l == 2) {
                if (messageSplit[1].equals("#p_slice"))
                    bot.sendNotice(user, "No, I refuse to.");
                else if (messageSplit[1].startsWith("#"))
                    bot.partChannel(bot.getChannel(messageSplit[1]));
                else
                    Command.throwUnknownChannelError(user, messageSplit[1]);
            } else
                Command.throwIncorrectParametersError(user, "+leave (channel)");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
