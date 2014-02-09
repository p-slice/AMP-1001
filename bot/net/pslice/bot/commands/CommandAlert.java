package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandAlert extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandAlert()
    {
        super("alert", 10, "<event>", "Toggle alerts for server events", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: Any arguments that were sent with the command
     * This command will attempt to toggle the state of
     *     an alert, specified by the user
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        PropertiesManager propertiesManager = bot.getPropertiesManager();

        // Should only have one arg - the name of the alert
        if (args.length == 1)
        {
            String alert = args[0].toLowerCase();

            if (alert.equals("on"))
            {
                for (String setAlert : propertiesManager.getAlerts())
                    while (!propertiesManager.getAlertState(setAlert))
                        propertiesManager.toggleAlert(setAlert);
                bot.sendMessage(channel, "All alerts turned on.");
            }

            else if (alert.equals("off"))
            {
                for (String setAlert : propertiesManager.getAlerts())
                    while (propertiesManager.getAlertState(setAlert))
                        propertiesManager.toggleAlert(setAlert);
                bot.sendMessage(channel, "All alerts turned off.");
            }

            // Check if the alert exists
            else if (propertiesManager.isAlert(alert))
            {
                propertiesManager.toggleAlert(alert);
                bot.sendMessage(channel, String.format("The alert for '%s' has been set to %b", alert, propertiesManager.getAlertState(alert)));
            }

            // Throw an error if it doesn't
            else
                CommandManager.throwGenericError(bot, sender, String.format("The alert '%s' was not recognized!", alert));
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
