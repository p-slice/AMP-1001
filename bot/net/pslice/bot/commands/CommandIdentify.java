package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandIdentify implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * This command will attempt to log the bot in with
     *     NickServ. The password found in the bot.properties
     *     file is used
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Command requires no arguments
        if (args.length == 0)
        {
            String pass = bot.getPropertiesManager().getProperty("nickservPASS");

            // Check if there is a password saved
            if (!pass.isEmpty() && !pass.equals(""))
            {
                bot.identify(pass);

                // Check if the bot actually identified
                if (!bot.getUserBot().isVerified())
                    CommandManager.throwGenericError(bot, sender, "Error: Couldn't identify (Incorrect password supplied?)");
            }

            // Throw an error if there isn't
            else
                CommandManager.throwGenericError(bot, sender, "Error: No password was supplied to log in with!");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
