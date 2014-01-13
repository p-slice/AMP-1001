package net.pslice.bot.commands;

import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public final class CommandKick implements Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (channel.isOp(bot.getUserBot()))
        {
            if (args.length == 1)
            {
                if (bot.getUser(args[0]).getChannels().contains(channel))
                    bot.kick(channel, bot.getUser(args[0]), "Kicked from channel");
                else
                    CommandManager.throwGenericError(bot, sender, String.format("Error: The user '%s' is not in this channel!", args[0]));
            }
            else if (args.length > 1)
            {
                if (bot.getUser(args[0]).getChannels().contains(channel))
                {
                    String reason = args[1];
                    for (int i = 2; i < args.length; i++)
                        reason += " " + args[i];
                    bot.kick(channel, bot.getUser(args[0]), reason);
                }
                else
                    CommandManager.throwGenericError(bot, sender, String.format("Error: The user '%s' is not in this channel!", args[0]));
            }
            else
                CommandManager.throwIncorrectParametersError(bot, sender, command);
        }
        else
            bot.sendMessage(channel, "I would if I could but I can't so I won't.");

    }
}
