package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandOverride extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandOverride()
    {
        super("override", 10, "", "Toggle Override mode", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will attempt to put the bot in
     *     Override mode. It can only be run by the bot
     *     Master, and does not use set ranks like other commands
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Check if the sender of the command is the bot Master
        if (sender.getNick().equals(bot.getPropertiesManager().getProperty("master")))
        {

            // Command requires no arguments
            if (args.length == 0)
            {
                bot.toggleOverride();

                // Return the new state of override
                if (bot.isOverride())
                    bot.sendMessage(channel, "Now in override mode. All commands from non-bot operators will be ignored.");
                else
                    bot.sendMessage(channel, "Override mode now disabled.");
            }

            // Throw an error if the parameters are incorrect
            else
                CommandManager.throwIncorrectParametersError(bot, sender, this);
        }

        // Throw an error if it isn't
        else
            CommandManager.throwGenericError(bot, sender, "You do not have permission to do that command!");
    }
}
