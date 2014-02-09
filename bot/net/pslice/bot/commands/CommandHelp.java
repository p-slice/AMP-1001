package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandHelp extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandHelp()
    {
        super("help", 0, "(command)", "Get info about commands", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: Any arguments that were sent with the command
     * This command will send information about a command to
     *     the sender. If no command is specified, a list of
     *     possible commands is returned
     * The user can also specify a number as an argument;
     *     this will return a list of all commands
     *     requiring that rank
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        CommandManager commandManager = bot.getCommandManager();

        // No args - send list of all commands
        if (args.length == 0)
        {
            bot.sendNotice(sender, "List of Commands:");
            bot.sendNotice(sender, String.format("(Use \u00034%shelp <command>\u00031 for specific command help)", bot.getPropertiesManager().getProperty("prefix")));

            // Send info for each command. To reduce spam, only commands within 5 ranks of the user's are sent
            for (String cmd : commandManager.getCommands())
            {
                Command command = commandManager.getCommand(cmd);
                if (bot.getUserManager().getRank(sender.getNick()) + 5 >= command.getRank())
                    bot.sendNotice(sender, String.format("\u0002%s\u000F: %s", command.name, command.getDescription()));
            }
        }

        // One arg - attempt to get info about a specific command
        else if (args.length == 1)
        {
            if (args[0].matches("\\d+"))
            {
                int rank = Integer.parseInt(args[0]);
                bot.sendNotice(sender, "List of Commands requiring the rank " + args[0] + ":");
                for (String cmd : commandManager.getCommands())
                {
                    Command command = commandManager.getCommand(cmd);
                    if (command.getRank() == rank)
                        bot.sendNotice(sender, String.format("\u0002%s\u000F: %s", command.name, command.getDescription()));
                }
            }

            else
            {
                String helpCommand = args[0].toLowerCase();

                // Check if the command exists
                if (commandManager.isCommand(helpCommand))
                {
                    Command command = commandManager.getCommand(helpCommand);

                    bot.sendNotice(sender, String.format("Help for command '\u00034%s\u000F':", helpCommand));
                    bot.sendNotice(sender, String.format("\u0002Required rank\u000F: %d", command.getRank()));
                    bot.sendNotice(sender, String.format("\u0002Parameters\u000F: %s%s %s", bot.getPropertiesManager().getProperty("prefix"), command.name, command.getParameters()));
                    bot.sendNotice(sender, String.format("\u0002Usage\u000F: %s", command.getDescription()));

                }

                // Throw an error if it doesn't
                else
                    CommandManager.throwUnknownCommandError(bot, sender, helpCommand);
            }
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
