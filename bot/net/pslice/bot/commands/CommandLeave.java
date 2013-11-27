package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandLeave extends Command {

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
                else if (messageSplit[1].startsWith("#")) {
                    if (!bot.channelExists(messageSplit[1]))
                        throwUnknownChannelError(user, messageSplit[1]);
                    else if (!bot.getChannelsNames().contains(messageSplit[1]))
                        throwGenericError(user, "Error: Not already in channel!");
                    else
                        bot.partChannel(bot.getChannel(messageSplit[1]));
                } else
                    throwUnknownChannelError(user, messageSplit[1]);
            } else
                throwIncorrectParametersError(user, "+leave (channel)");
        } else
            throwNoRankError(user, rank, p);
    }
}
