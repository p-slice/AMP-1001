package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandSay extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandSay()
    {
        super("say", 7, "(#channel) <message>", "Send a message to a channel", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will make the bot attempt to send
     *     a message to a channel, either the channel the
     *     command was sent in or one specified by the
     *     sender. If the bot is not in the channel, it
     *     will join, say the message, then leave again
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // If the first word starts with '#', the bot will attempt to send the message to that channel
        if (args.length > 1
                && args[0].startsWith("#"))
        {
            String message = args[1];
            for (int i = 2; i < args.length; i++)
                message += " " + args[i];

            boolean inChannel = false;

            // Check if the bot is in the channel already
            if (bot.getChannel(args[0]).getUsers().contains(bot.getUserBot()))
                inChannel = true;

            // If the bot isn't in the channel, join it
            if (!inChannel)
                bot.joinChannel(args[0]);

            bot.sendMessage(args[0], message);

            // If the bot wasn't in the channel, leave it
            if (!inChannel)
                bot.partChannel(bot.getChannel(args[0]));
        }

        // Otherwise the message is sent in the original channel
        else if (args.length > 0)
        {
            String message = args[0];
            for (int i = 1; i < args.length; i++)
                message += " " + args[i];
            bot.sendMessage(channel, message);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
