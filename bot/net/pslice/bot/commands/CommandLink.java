package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandLink implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        PropertiesManager propertiesManager = bot.getPropertiesManager();

        if (args.length == 1)
        {
            String link = args[0].toLowerCase();
            if (propertiesManager.isLink(link))
                bot.sendMessage(channel, String.format("Link: %s", propertiesManager.getLink(link)));
            else
                CommandManager.throwGenericError(bot, sender, String.format("Error: The link '%s' was not recognized!", link));
        }
        else if (args.length == 2)
        {
            String link = args[0].toLowerCase();
            propertiesManager.setLink(link, args[1]);
            bot.sendMessage(channel, "Link saved.");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
