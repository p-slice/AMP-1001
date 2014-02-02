package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandRestart implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command will make the bot attempt to disconnect and
     *     then reconnect to the IRC server
     * NOTE: This command is currently broken, it seems to throw
     *          an exception with every use. It cannot be run
     *          right now
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Command requires no arguments
        if (args.length == 0)
        {
            try
            {
                bot.reconnect();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
