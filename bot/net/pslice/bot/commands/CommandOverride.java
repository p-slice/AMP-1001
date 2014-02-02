package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandOverride implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (sender.getNick().equals(bot.getPropertiesManager().getProperty("master")))
        {
            if (args.length == 0)
            {
                bot.toggleOverride();
                if (bot.isOverride())
                    bot.sendMessage(channel, "Now in override mode. All commands from non-bot operators will be ignored.");
                else
                    bot.sendMessage(channel, "Override mode now disabled.");
            }
            else
                CommandManager.throwIncorrectParametersError(bot, sender, command);
        }
        else
            CommandManager.throwGenericError(bot, sender, "Error: You do not have permission to do that command!");
    }
}
