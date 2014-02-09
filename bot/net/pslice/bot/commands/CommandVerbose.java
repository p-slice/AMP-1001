package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandVerbose extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandVerbose()
    {
        super("verbose", 10, "", "Toggle verbosity to the console", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command toggles whether or not raw info is
     *     sent to the console
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires no arguments
        if (args.length == 0)
        {
            bot.setVerbose(!bot.isVerbose());

            // Return the new state of verbose
            if (bot.isVerbose())
                bot.sendMessage(channel, "Verbose now turned on.");
            else
                bot.sendMessage(channel, "Verbose now turned off.");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
