package net.pslice.bot.managers;

import net.pslice.bot.AmpBot;
import net.pslice.bot.BotProperties;
import net.pslice.bot.Constants;
import net.pslice.bot.commands.*;
import org.pircbotx.User;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public final class CommandManager implements Constants {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    private HashMap<String, Command>

            // Map of all command names to their objects
            commands = new HashMap<>();

    private final FileManager

            // The file manager used by the bot
            fileManager;





    /**
     * ===========================================
     * Initializer:
     *
     * @param fileManager: The file manager used by the bot
     * ===========================================
     */

    public CommandManager(FileManager fileManager)
    {
        this.fileManager = fileManager;
    }





    /**
     * ===========================================
     * Getter for whether or not a command exists:
     *
     * @param name: The name of the command
     * @return Whether or not the command exists
     * ===========================================
     */

    public boolean isCommand(String name)
    {
        return commands.containsKey(name);
    }





    /**
     * ===========================================
     * Getter for a command:
     *
     * @param name: The name of the command
     * @return The command object
     * ===========================================
     */

    public Command getCommand(String name)
    {
        return commands.get(name);
    }





    /**
     * ===========================================
     * Getter for a set of all command names
     *
     * @return The set of all command names
     * ===========================================
     */

    public Set<String> getCommands()
    {
        return commands.keySet();
    }





    /*
     * ===========================================
     * Method to load maps
     *
     * The bot will attempt to load files containing the maps
     * If the necessary files cannot be found, defaults are
     *     used and saved
     * ===========================================
     */

    public void loadFiles()
    {
        // Load file for command objects
        if (fileManager.fileExists(command_location))
            commands = fileManager.load(command_location);
        else
        {
            commands = BotProperties.defaultCommands();
            fileManager.save(commands, command_location);
        }
    }





    /*
     * ===========================================
     * Method to save all commands
     * ===========================================
     */

    public void saveCommands()
    {
        fileManager.save(commands, command_location);
    }





    /*
     * ===========================================
     * Method to throw an Error:
     *      Unknown Command
     *
     * This error occurs when
     *  1) A message is sent beginning with the command prefix
     *      without a recognized command
     *  2) A user tries to change a setting of a non-existent
     *      command
     * The user is notified of the incorrect command
     * ===========================================
     */

    public static void throwUnknownCommandError(AmpBot bot, User sender, String command)
    {
        throwGenericError(bot, sender, String.format("The command '%s' was not recognized! Type '%shelp' for a list of commands",
                command, bot.getPropertiesManager().getProperty("prefix")));
    }





   /*
     * ===========================================
     * Method to throw an Error:
     *      Insufficient Rank
     *
     * This error occurs when
     *  A user attempts to perform a command with a rank
     *      lower than the rank required by the command
     * The user is notified of their own rank and the required rank
     * ===========================================
     */

    public static void throwInsufficientRankError(AmpBot bot, User sender, int p, int r)
    {
        throwGenericError(bot, sender, String.format("Insufficient permissions! (Your rank: %d Required rank: %s)", p, r));
    }





    /*
     * ===========================================
     * Method to throw an Error:
     *      Incorrect Parameters
     *
     * This error occurs when
     *  A user tries to perform a command with incorrect
     *      parameters
     * The user is notified of the proper parameters for
     *  the command
     * ===========================================
     */

    public static void throwIncorrectParametersError(AmpBot bot, User sender, Command command)
    {
        throwGenericError(bot, sender, String.format("Incorrect parameters! (Command usage: %s%s %s)",
                bot.getPropertiesManager().getProperty("prefix"), command.name, command.getParameters()));
    }





    /*
     * ===========================================
     * Method to throw an Error:
     *      Unknown Channel
     *
     * This error occurs when
     *  1) The bot tries to join a non-existent channel
     *  2) The bot tries to leave a non-existent channel
     * The user is notified of the unknown channel
     * ===========================================
     */

    public static void throwUnknownChannelError(AmpBot bot, User sender, String channel)
    {
        throwGenericError(bot, sender, String.format("The channel '%s' was not recognized!", channel));
    }





    /*
     * ===========================================
     * Method to throw an Error:
     *      Unknown Setting
     *
     * This error occurs when
     *  1) A user tries to change an unknown command setting
     *  2) A user tries to change an unknown user setting
     *  3) A user tries to view the unknown setting of a user
     * The user is notified ot the unknown setting
     * ===========================================
     */

    public static void throwUnknownSettingError(AmpBot bot, User sender, String setting)
    {
        throwGenericError(bot, sender, String.format("The setting '%s' was not recognized!", setting));
    }





    /*
     * ===========================================
     * Method to throw an Error:
     *      Generic
     *
     * This error occurs when
     *  None of the previous errors are suited to
     *      the error type
     * The user is notified with a message written at
     *  the source of the error
     * ===========================================
     */

    public static void throwGenericError(AmpBot bot, User sender, String message)
    {
        bot.sendNotice(sender, "\u00034Error\u000F: " + message);
    }
}
