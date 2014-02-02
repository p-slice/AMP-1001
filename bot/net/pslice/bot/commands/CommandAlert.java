package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandAlert implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 1)
        {
            String alert = args[0].toLowerCase();
            if (bot.getPropertiesManager().isAlert(alert))
            {
                bot.getPropertiesManager().toggleAlert(alert);
                bot.sendMessage(channel, String.format("The alert for '%s' has been set to %b", alert, bot.getPropertiesManager().getAlertState(alert)));
            }
            else
                CommandManager.throwGenericError(bot, sender, String.format("Error: The alert '%s' was not recognized!", alert));
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
