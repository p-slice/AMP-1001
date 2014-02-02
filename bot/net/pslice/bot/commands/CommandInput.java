package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandInput implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * This command will toggle how the bot assigned ranks
     *     to users
     * There are two ways:
     *  1) With assigned ranks, found in the users.ranks file
     *  2) With channel ranks (Op, Voice, etc)
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Command requires no arguments
        if (args.length == 0)
        {
            bot.toggleRankInput();

            // Send a message of what the new input method is
            if (bot.usesChannelRanks())
                bot.sendMessage(channel, "Input method now based on channel permissions.");
            else
                bot.sendMessage(channel, "Input method now based on assigned ranks");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
