package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandLink implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command will attempt to retrieve a saved link
     *     from the util.links file and return it. Adding a
     *     link after the name will save that link
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        PropertiesManager propertiesManager = bot.getPropertiesManager();

        // One argument - retrieve a link
        if (args.length == 1)
        {
            String link = args[0].toLowerCase();

            // Check if the link exists
            if (propertiesManager.isLink(link))
                bot.sendMessage(channel, String.format("Link: %s", propertiesManager.getLink(link)));

            // Throw an error if it doesn't
            else
                CommandManager.throwGenericError(bot, sender, String.format("The link '%s' was not recognized!", link));
        }

        // Two arguments - save a link
        else if (args.length == 2)
        {
            String link = args[0].toLowerCase();
            propertiesManager.setLink(link, args[1]);
            bot.sendMessage(channel, "Link saved.");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
