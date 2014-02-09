package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandSet extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandSet()
    {
        super("setcommand", 10, "<setting> <name> <value>", "Change the setting of a command", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will change the settings of a specified command
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires three args - the setting, the name of the command and the new value
        if (args.length >= 3)
        {
            CommandManager commandManager = bot.getCommandManager();

            String setting = args[0].toLowerCase();
            String editCommand = args[1].toLowerCase();

            // Check if the command exists
            if (commandManager.isCommand(editCommand))
            {
                Command command = commandManager.getCommand(editCommand);
                // Check which setting is being changed
                switch (setting)
                {

                    // Change the required tank for the command
                    case "rank":
                    case "r":

                        // Make sure the new rank is numbers only
                        if (args[2].matches("-?[\\d]+")) {
                            command.setRank(Integer.parseInt(args[2]));
                            bot.sendMessage(channel, String.format("The required rank for '%s' is now %s", editCommand, args[2]));
                        }

                        // Throw an error if it isn't
                        else
                            CommandManager.throwGenericError(bot, sender, "Rank must be a number!");
                        break;

                    // Change the parameters for the command
                    case "parameters":
                    case "params":
                    case "p":
                        String params = args[2];
                        for (int i = 3; i < args.length; i++)
                            params += " " + args[i];
                        command.setParameters(params);
                        bot.sendMessage(channel, String.format("The parameters for '%s' are now '%s'", editCommand, params));
                        break;

                    // Change the description of the command
                    case "description":
                    case "desc":
                    case "d":
                        String desc = args[2];
                        for (int i = 3; i < args.length; i++)
                            desc += " " + args[i];
                        command.setDescription(editCommand);
                        bot.sendMessage(channel, String.format("The description for '%s' is now '%s'", editCommand, desc));
                        break;

                    // Change whether or not the command is enabled
                    case "enabled":
                    case "e":

                        // No one is allowed to disable these
                        if (editCommand.matches("setcommand|setuser|override|quit|reload|setproperty"))
                        {
                            CommandManager.throwGenericError(bot, sender, "Changing that setting is denied.");
                            return;
                        }

                        String enabled = args[2].toLowerCase();
                        switch (enabled) {
                            case "true":
                                command.setEnabled(true);
                                bot.sendMessage(channel, String.format("The command '%s' is now enabled", editCommand));
                                break;
                            case "false":
                                command.setEnabled(false);
                                bot.sendMessage(channel, String.format("The command '%s' is now disabled", editCommand));
                                break;
                            default:
                                CommandManager.throwGenericError(bot, sender, "You can only set the enabled state to true or false!");
                                break;
                        }
                        break;

                    // The setting is not recognized - throw an error
                    default:
                        CommandManager.throwUnknownSettingError(bot, sender, setting);
                        return;
                }
                commandManager.saveCommands();
            }

            // Throw an error if not a command
            else
                CommandManager.throwUnknownCommandError(bot, sender, editCommand);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
