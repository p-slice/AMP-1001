package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandReset extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandReset()
    {
        super("reset", 10, "<command>", "Reset a command to default values", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will reset the settings of a specified
     *     command to their defaults
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Command requires one argument - the name of the command
        if (args.length == 1)
        {
            // Check if the command exists
            if (bot.getCommandManager().isCommand(args[0].toLowerCase()))
            {
                Command command = bot.getCommandManager().getCommand(args[0].toLowerCase());
                command.reset();
                bot.sendMessage(channel, "The command " + args[0].toLowerCase() + " has been reset.");
                bot.getCommandManager().saveCommands();
            }

            // Throw an error if it doesn't
            else
                CommandManager.throwUnknownCommandError(bot, sender, args[0]);
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
