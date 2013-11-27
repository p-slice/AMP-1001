package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.Random;

class CommandTwerk extends Command {

    private final static Random rand = new Random();

    public static void execute(Channel chan, User user, int l, int p, int rank) {

        if (p >= rank) {
            if (chan.isOp(bot.getUserBot())) {
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
                    throwIncorrectParametersError(user, "+twerk");
            } else
                bot.sendMessage(chan, "Sorry, I don't feel up to watching people twerk for me right now.");
        } else
            throwNoRankError(user, rank, p);
    }
}
