package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandAlert implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * This command will attempt to toggle the state of
     *     an alert, specified by the user
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Should only have one arg - the name of the alert
        if (args.length == 1)
        {
            String alert = args[0].toLowerCase();

            // Check if the alert exists
            if (bot.getPropertiesManager().isAlert(alert))
            {
                bot.getPropertiesManager().toggleAlert(alert);
                bot.sendMessage(channel, String.format("The alert for '%s' has been set to %b", alert, bot.getPropertiesManager().getAlertState(alert)));
            }

            // Throw an error if it doesn't
            else
                CommandManager.throwGenericError(bot, sender, String.format("Error: The alert '%s' was not recognized!", alert));
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
