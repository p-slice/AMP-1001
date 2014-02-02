package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandSet implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command will change the settings of a specified command
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
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
                // Check which setting is being changed
                switch (setting)
                {

                    // Change the required tank for the command
                    case "rank":
                    case "r":

                        // Make sure the new rank is numbers only
                        if (args[2].matches("-?[\\d]+")) {
                            commandManager.setRank(editCommand, Integer.parseInt(args[2]));
                            bot.sendMessage(channel, String.format("The required rank for '%s' is now %s", editCommand, args[2]));
                        }

                        // Throw an error if it isn't
                        else
                            CommandManager.throwGenericError(bot, sender, "Error: Rank must be a number!");
                        break;

                    // Change the parameters for the command
                    case "parameters":
                    case "params":
                    case "p":
                        String params = args[2];
                        for (int i = 3; i < args.length; i++)
                            params += " " + args[i];
                        commandManager.setParameters(editCommand, params);
                        bot.sendMessage(channel, String.format("The parameters for '%s' are now '%s'", editCommand, params));
                        break;

                    // Change the description of the command
                    case "description":
                    case "desc":
                    case "d":
                        String desc = args[2];
                        for (int i = 3; i < args.length; i++)
                            desc += " " + args[i];
                        commandManager.setDescription(editCommand, desc);
                        bot.sendMessage(channel, String.format("The description for '%s' is now '%s'", editCommand, desc));
                        break;

                    // The setting is not recognized - throw an error
                    default:
                        CommandManager.throwUnknownSettingError(bot, sender, setting);
                        break;
                }
            }

            // Throw an error if not a command
            else
                CommandManager.throwUnknownCommandError(bot, sender, command);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
