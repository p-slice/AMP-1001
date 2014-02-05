package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandHelp implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * This command will send information about a command to
     *     the sender. If no command is specified, a list of
     *     possible commands is returned
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        CommandManager commandManager = bot.getCommandManager();

        // No args - send list of all commands
        if (args.length == 0)
        {
            bot.sendNotice(sender, "List of Commands:");
            bot.sendNotice(sender, String.format("(Use \u00034%shelp <command>\u00031 for specific command help)", bot.getPropertiesManager().getProperty("prefix")));

            // Send info for each command. To reduce spam, only commands within 5 ranks of the user's are sent
            for (String helpCommand : commandManager.getCommands())
                if (bot.getUserManager().getRank(sender.getNick()) + 5 >= commandManager.getRank(helpCommand))
                    bot.sendNotice(sender, String.format("\u0002%s\u000F: %s", helpCommand, commandManager.getDescription(helpCommand)));
        }

        // One arg - attempt to get info about a specific command
        else if (args.length == 1)
        {
            String helpCommand = args[0].toLowerCase();

            // Check if the command exists
            if (commandManager.isCommand(helpCommand))
            {
                bot.sendNotice(sender, String.format("Help for command '\u00034%s\u00031':", helpCommand));
                bot.sendNotice(sender, String.format("\u0002Required rank\u000F: %d", commandManager.getRank(helpCommand)));
                bot.sendNotice(sender, String.format("\u0002Parameters\u000F: %s%s", bot.getPropertiesManager().getProperty("prefix"), commandManager.getParameters(helpCommand)));
                bot.sendNotice(sender, String.format("\u0002Usage\u000F: %s", commandManager.getDescription(helpCommand)));

            }

            // Throw an error if it doesn't
            else
                CommandManager.throwUnknownCommandError(bot, sender, helpCommand);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
