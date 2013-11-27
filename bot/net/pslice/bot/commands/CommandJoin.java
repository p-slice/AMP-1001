package net.pslice.bot.commands;

import org.pircbotx.User;

class CommandJoin extends Command {

    public static void execute(User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 2)
                if (messageSplit[1].startsWith("#")) {
                    bot.sendNotice(user, "Attempting to join " + messageSplit[1]);
                    if (!bot.channelExists(messageSplit[1]))
                        throwUnknownChannelError(user, messageSplit[1]);
                    else if (bot.getChannel(messageSplit[1]).isInviteOnly())
                        throwGenericError(user, "Error: Channel is invite only!");
                    if (bot.getChannelsNames().contains(messageSplit[1]))
                        throwGenericError(user, "Error: Already in channel!");
                    else
                        bot.joinChannel(messageSplit[1]);
                } else
                    throwUnknownChannelError(user, messageSplit[1]);
            else
                throwIncorrectParametersError(user, "+join <channel>");
        } else
            throwNoRankError(user, rank, p);
    }
}
