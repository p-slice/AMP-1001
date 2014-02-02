package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandKick implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * This command will make the bot attempt to kick a user
     *     from the channel the command was sent in. The sender can
     *     also specify a message to kick with
     * The command will only work if the bot has Op status in
     *     the channel
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Check if the bot has Op in the channel
        if (channel.isOp(bot.getUserBot()))
        {
            // One argument - The name of the user
            if (args.length == 1)
            {
                // Check if the user is in the channel
                if (bot.getUser(args[0]).getChannels().contains(channel))
                    bot.kick(channel, bot.getUser(args[0]), "Kicked from channel");

                // Throw an error if they aren't
                else
                    CommandManager.throwGenericError(bot, sender, String.format("Error: The user '%s' is not in this channel!", args[0]));
            }

            // Kick the user for a given reason
            else if (args.length > 1)
            {
                // Check if the user is in the channel
                if (bot.getUser(args[0]).getChannels().contains(channel))
                {
                    String reason = args[1];
                    for (int i = 2; i < args.length; i++)
                        reason += " " + args[i];
                    bot.kick(channel, bot.getUser(args[0]), reason);
                }

                // Throw an error if they aren't
                else
                    CommandManager.throwGenericError(bot, sender, String.format("Error: The user '%s' is not in that channel!", args[0]));
            }

            // Throw an error if the parameters are incorrect
            else
                CommandManager.throwIncorrectParametersError(bot, sender, command);
        }

        // Throw an error if not Op
            bot.sendMessage(channel, "I would if I could but I can't so I won't.");

    }
}
