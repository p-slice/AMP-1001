package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandSay extends Command {

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        String finalMessage;

        if (p >= rank) {
            if (l >= 3 && messageSplit[1].startsWith("#")) {
                if (!bot.channelExists(messageSplit[1]))
                    throwUnknownChannelError(user, messageSplit[1]);
                else {
                    boolean leaveChan = false;
                    if (!bot.getChannelsNames().contains(messageSplit[1])) {
                        if (bot.getChannel(messageSplit[1]).isInviteOnly())
                            throwGenericError(user, "Error: Couldn't join channel to send message!");
                        else {
                            bot.joinChannel(messageSplit[1]);
                            leaveChan = true;
                        }
                    } else {
                        finalMessage = messageSplit[2];
                        for (int i = 3; i < messageSplit.length; i++)
                            finalMessage += " " + messageSplit[i];
                        bot.sendMessage(messageSplit[1], finalMessage);
                    }
                    if (leaveChan)
                        bot.partChannel(bot.getChannel(messageSplit[1]));
                }
            } else if (l >= 2) {
                finalMessage = messageSplit[1];
                for (int i = 2; i < messageSplit.length; i++)
                    finalMessage += " " + messageSplit[i];
                bot.sendMessage(chan, finalMessage);
            } else
                throwIncorrectParametersError(user, "+say (channel) <message>");
        } else
            throwNoRankError(user, rank, p);
    }
}
