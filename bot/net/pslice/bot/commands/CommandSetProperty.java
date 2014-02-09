package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandSetProperty extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandSetProperty()
    {
        super("setproperty", 10, "<property> <value>", "Set a property of the bot", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will attempt to change a specified bot
     *     property to a new value
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires two arguments - the property and its new value
        if (args.length == 2)
        {
            String property = args[0].toLowerCase();
            String value = args[1];

            // Not allowed to change who the bot Master is
            if (property.equals("master"))
            {
                CommandManager.throwGenericError(bot, sender, "That property cannot be changed!");
                return;
            }

            // Check if the property exists
            if (bot.getPropertiesManager().isProperty(property))
            {
                bot.getPropertiesManager().setProperty(property, value);
                bot.sendMessage(channel, String.format("Property '%s' set to '%s'", property, value));
            }

            // Throw an error if it doesn't
            else
                CommandManager.throwGenericError(bot,sender, String.format("The property '%s' was not recognized!", property));
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
