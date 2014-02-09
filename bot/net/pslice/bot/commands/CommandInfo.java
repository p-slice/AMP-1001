package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandInfo extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandInfo()
    {
        super("info", 3, "<property>", "Get info about any bot property", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command returns info about the bot
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires one argument - the name of the property
        if (args.length == 1)
        {
            String property = args[0].toLowerCase();
            PropertiesManager propertiesManager = bot.getPropertiesManager();

            // Check which property is being queried
            switch (property)
            {
                // Get the default name of the bot
                case "name":
                case "nick":
                    bot.sendMessage(channel, "Default name: " + propertiesManager.getProperty("nick"));
                    break;

                // Get the default login of the bot
                case "login":
                    bot.sendMessage(channel, "Default login: " + propertiesManager.getProperty("login"));
                    break;

                // Get the current version of the bot
                case "version":
                    bot.sendMessage(channel, "Version: " + AmpBot.version);
                    break;

                // Get the passwork of the bot - HAHAHAHAHAHAHA No.
                case "password":
                    bot.sendMessage(channel, "That info is not public.");
                    break;

                // Get the default server the bot will connect to on startup
                case "server":
                    bot.sendMessage(channel, "Default server: " + propertiesManager.getProperty("server"));
                    break;

                // Get the default channels the bot will connect to on startup
                case "channels":
                    String[] channels = propertiesManager.getProperty("channels").split(" ");
                    String allChannels = channels[0];
                    for (int i = 1; i < channels.length; i++)
                        allChannels += ", " + channels[i];
                    bot.sendMessage(channel, "Default channels: " + allChannels);
                    break;

                // Get the name of the bot's master
                case "master":
                    bot.sendMessage(channel, "Master: " + propertiesManager.getProperty("master"));
                    break;

                // The property wasn't recognized - throw an error
                default:
                    CommandManager.throwGenericError(bot, sender, String.format("The property '%s' was not recognized!", property));
            }
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
