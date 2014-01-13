package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public final class CommandProperties implements Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 2)
        {
            String property = args[0].toLowerCase();
            String value = args[1];

            if (AmpBot.isProperty(property))
            {
                AmpBot.setProperty(property, value);
                bot.sendMessage(channel, String.format("Property '%s' set to '%s'", property, value));
            }
            else
                CommandManager.throwGenericError(bot,sender, String.format("Error: The property '%s' was not recognized!", property));
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
