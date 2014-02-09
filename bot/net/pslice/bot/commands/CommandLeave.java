package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandLeave extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandLeave()
    {
        super("leave", 7, "(#channel)", "Leave a channel", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will make the bot attempt to leave
     *     a channel, either the one the the command was
     *     sent in, or a channel specified by the sender
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // No arguments - leave the channel the command was sent in
        if (args.length == 0)
            bot.partChannel(channel, String.format("Leave command by '%s'", sender.getNick()));

            // One argument - leave a specified channel
        else if (args.length == 1)
        {
            // Check if the channel exists
            if (args[0].startsWith("#"))
                bot.partChannel(bot.getChannel(args[0]), String.format("Leave command by '%s'", sender.getNick()));

                // Throw an error if it doesn't
            else
                CommandManager.throwUnknownChannelError(bot, sender, args[0]);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
