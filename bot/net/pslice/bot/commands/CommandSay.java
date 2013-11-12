package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandSay {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        String finalMessage;

        if (p >= rank) {
            if (l >= 3 && messageSplit[1].startsWith("#")) {
                finalMessage = messageSplit[2];
                for (int i = 3; i < messageSplit.length; i++)
                    finalMessage += " " + messageSplit[i];
                bot.sendMessage(messageSplit[1], finalMessage);
            } else if (l >= 2) {
                finalMessage = messageSplit[1];
                for (int i = 2; i < messageSplit.length; i++)
                    finalMessage += " " + messageSplit[i];
                bot.sendMessage(chan, finalMessage);
            } else
                Command.throwIncorrectParametersError(user, "+say (channel) <message>");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
