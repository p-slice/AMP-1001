package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Random;

class CommandTwerk {

    private static final PircBotX bot = AMP.getBot();
    private final static Random rand = new Random();

    public static void execute(Channel chan, User user, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 1) {
                int[] willKick = {0, 0, 1};
                bot.sendMessage(chan, user.getNick() + " is going to twerk for us now...");
                bot.sendMessage(chan, "But do they know how to twerk safely?");
                int k = willKick[rand.nextInt(willKick.length)];
                if (k == 0) {
                    bot.kick(chan, user, "You twerked your way right out of the channel!");
                    bot.sendMessage(chan, "Nope!");
                } else if (k == 1) {
                    bot.sendMessage(chan, "Looks like " + user.getNick() + " is good at twerking... for now...");
                }
            } else
                Command.throwIncorrectParametersError(user, "+twerk");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
