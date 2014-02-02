package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.Probability;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandTwerk implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            if(channel.getOps().contains(bot.getUserBot()))
            {
                bot.sendMessage(channel, String.format("Looks like %s is willing to twerk for us now...", sender.getNick()));
                bot.sendMessage(channel, "But do they have what it takes to be a professional?");

                Probability<Boolean> probability = new Probability<>();
                probability.add(true, 2);
                probability.add(false, 1);

                if(probability.getResult())
                    bot.sendMessage(channel, String.format("Wow, %s sure is skilled! Soon they'll be twerking for all their friends!", sender.getNick()));
                else
                {
                    bot.sendMessage(channel, String.format("Oh dear, %s, you'll have to do better than that.", sender.getNick()));
                    bot.kick(channel, sender, "You've twerked your way right out of the channel!");
                }
            }
            else
                channel.sendMessage("Sorry, I don't feel fit for judging twerk sessions right now.");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
