package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandJoin extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandJoin()
    {
        super("join", 7, "<#channel>", "Join a channel", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: Any arguments that were sent with the command
     * This command will make the bot attempt to join
     *     a specified channel
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires one argument - The name of the channel
        if (args.length == 1)
        {
            // Check if the channel name starts with '#'
            if (args[0].startsWith("#"))
                bot.joinChannel(args[0]);

                // Throw an error if it doesn't
            else
                CommandManager.throwUnknownChannelError(bot, sender, args[0]);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
