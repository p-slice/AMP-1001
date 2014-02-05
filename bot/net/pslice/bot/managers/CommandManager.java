package net.pslice.bot.managers;

import net.pslice.bot.AmpBot;
import net.pslice.bot.BotProperties;
import net.pslice.bot.Constants;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.Set;

public final class CommandManager implements Constants {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    private HashMap<String, Class<?>>

            // Map of all classes for commands
            classes;

    private HashMap<String, Integer>

            // Map of all ranks for commands
            ranks;

    private HashMap<String, String>

            // Map of all parameters for commands
            parameters,

            // Map of all descriptions for commands
            descriptions;

    private HashMap<String, Boolean>

            // Map of all enabled states for commands
            enabled;

    private final FileManager

            // File manager used by the bot
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





    /*
     * ===========================================
     * Method to load maps:
     *
     * The bot will attempt to load files containing the maps
     * If the necessary files cannot be found, defaults are
     *     used and saved
     * ===========================================
     */

    public void loadFiles()
    {
        // Load files for command classes
        if (fileManager.fileExists(command_class_location))
            classes = fileManager.load(command_class_location);
        else
        {
            classes = BotProperties.defaultClasses();
            fileManager.save(classes, command_class_location);
        }

        // Load files for command ranks
        if (fileManager.fileExists(command_rank_location))
            ranks = fileManager.load(command_rank_location);
        else
        {
            ranks = BotProperties.defaultRanks();
            fileManager.save(ranks, command_rank_location);
        }

        // Load files for command parameters
        if (fileManager.fileExists(command_parameter_location))
            parameters = fileManager.load(command_parameter_location);
        else
        {
            parameters = BotProperties.defaultParameters();
            fileManager.save(parameters, command_parameter_location);
        }

        // Load files for command descriptions
        if (fileManager.fileExists(command_description_location))
            descriptions = fileManager.load(command_description_location);
        else
        {
            descriptions = BotProperties.defaultDescriptions();
            fileManager.save(descriptions, command_description_location);
        }

        // Load files for command enabled states
        if (fileManager.fileExists(command_enabled_location))
            enabled = fileManager.load(command_enabled_location);
        else
        {
            enabled = BotProperties.defaultEnabledStates();
            fileManager.save(enabled, command_enabled_location);
        }
    }





    /**
     * ===========================================
     * Getter for the class a command is located in:
     *
     * @param command: The command being used
     * @return The class assigned to the command
     *         Null if no class is found
     * ===========================================
     */

    public Class<?> getClass(String command)
    {
        return classes.containsKey(command) ? classes.get(command) : null;
    }





    /**
     * ===========================================
     * Setter for the rank of a command:
     *
     * @param command: The command being set
     * @param rank: The new rank of the command
     * If the rank is equal to 0, it is removed from
     *  the map
     * Upon setting the new rank, the file is saved
     * ===========================================
     */

    public void setRank(String command, int rank)
    {
        ranks.put(command, rank);
        if (rank == 0)
            ranks.remove(command);
        fileManager.save(ranks, command_rank_location);
    }





    /**
     * ===========================================
     * Getter for the rank of a command:
     *
     * @param command: The command being used
     * @return The rank assigned to the command
     *         0 if no rank is found
     * ===========================================
     */

    public int getRank(String command)
    {
        return ranks.containsKey(command) ? ranks.get(command) : 0;
    }





    /**
     * ===========================================
     * Setter for the parameters of a command:
     *
     * @param command: The command being set
     * @param parameters: The new parameters for the command
     * If the parameters are empty, they are removed from
     *  the map
     * Upon setting the new parameters, the file is saved
     * ===========================================
     */

    public void setParameters(String command, String parameters)
    {
        this.parameters.put(command, parameters);
        if (parameters.equals(""))
            this.parameters.remove(parameters);
        fileManager.save(this.parameters, command_parameter_location);
    }





    /**
     * ===========================================
     * Getter for the parameters of a command:
     *
     * @param command: The command being used
     * @return The parameters assigned to the command
     *         "(None Assigned)" if no parameters are found
     * ===========================================
     */

    public String getParameters(String command)
    {
        return parameters.containsKey(command) ? parameters.get(command) : "(None Assigned)";
    }





    /**
     * ===========================================
     * Setter for the description of a command:
     *
     * @param command: The command being set
     * @param description: The new description of the command
     * If the description is empty, it is removed from
     *  the map
     * Upon setting the new description, the file is saved
     * ===========================================
     */

    public void setDescription(String command, String description)
    {
        descriptions.put(command, description);
        if (description.equals(""))
            descriptions.remove(command);
        fileManager.save(descriptions, command_description_location);
    }





    /**
     * ===========================================
     * Getter for the description of a command:
     *
     * @param command: The command being used
     * @return The description assigned to the command
     *         "(None Assigned)" if no description is found
     * ===========================================
     */

    public String getDescription(String command)
    {
        return descriptions.containsKey(command) ? descriptions.get(command) : "(None Assigned)";
    }





    /**
     * ===========================================
     * Setter for the enabled state of a command:
     *
     * @param command: The command being set
     * @param enabled: The new enabled state
     * Upon setting the new state, the file is saved
     * ===========================================
     */

    public void setEnabled(String command, boolean enabled)
    {
        this.enabled.put(command, enabled);
        fileManager.save(this.enabled, command_enabled_location);
    }





    /**
     * ===========================================
     * Getter for the enabled state of a command:
     *
     * @param command: The command being used
     * @return Whether or not the command is enabled
     * ===========================================
     */

    public boolean isEnabled(String command)
    {
        return enabled.get(command);
    }





    /**
     * ===========================================
     * Getter for whether or not a command exists
     *
     * @param command: The command being checked
     * @return Whether or not the command exists
     * Existence is based on whether or not the command
     *     is found in both the classes and ranks maps
     * Parameters and descriptions maps are not checked
     *     as they are not required for the command to
     *     function
     * ===========================================
     */

    public boolean isCommand(String command)
    {
        return classes.containsKey(command) && ranks.containsKey(command);
    }





    /**
     * ===========================================
     * Getter for the set of all possible commands:
     *
     * @return All known commands
     * The set of commands from the classes map is used
     *     because it is impossible for a command to work
     *     without a class assigned to it.
     * ===========================================
     */

    public Set<String> getCommands()
    {
        return classes.keySet();
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

    public static void throwIncorrectParametersError(AmpBot bot, User sender, String command)
    {
        throwGenericError(bot, sender, String.format("Incorrect parameters! (Command usage: %s%s)",
                bot.getPropertiesManager().getProperty("prefix"), bot.getCommandManager().getParameters(command)));
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
        bot.sendNotice(sender, "\u00034Error\u00031: " + message);
    }
}
