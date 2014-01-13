package net.pslice.bot.managers;

import net.pslice.bot.BotProperties;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.Set;

public final class CommandManager {

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private static HashMap<String, Class<?>> classes;
    private static HashMap<String, Integer> ranks;
    private static HashMap<String, String> parameters;
    private static HashMap<String, String> descriptions;

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private CommandManager(){}

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void loadFiles()
    {
        if (FileManager.fileExists("commands.classes"))
            classes = FileManager.load("commands.classes");
        else
        {
            classes = BotProperties.defaultClasses();
            FileManager.save(classes, "commands.classes");
        }

        if (FileManager.fileExists("commands.ranks"))
            ranks = FileManager.load("commands.ranks");
        else
        {
            ranks = BotProperties.defaultRanks();
            FileManager.save(ranks, "commands.ranks");
        }

        if (FileManager.fileExists("commands.parameters"))
            parameters = FileManager.load("commands.parameters");
        else
        {
            parameters = BotProperties.defaultParameters();
            FileManager.save(parameters, "commands.parameters");
        }

        if (FileManager.fileExists("commands/descriptions"))
            descriptions = FileManager.load("commands.descriptions");
        else
        {
            descriptions = BotProperties.defaultDescriptions();
            FileManager.save(descriptions, "commands.descriptions");
        }
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static Class<?> getClass(String command)
    {
        if (classes.containsKey(command))
            return classes.get(command);
        return null;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void setRank(String command, int rank)
    {
        ranks.put(command, rank);
        FileManager.save(ranks, "commands.ranks");
    }

    public static Integer getRank(String command)
    {
        if (ranks.containsKey(command))
            return ranks.get(command);
        return 0;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void setParameters(String command, String parameter)
    {
        parameters.put(command, parameter);
        FileManager.save(parameters, "commands.parameters");
    }

    public static String getParameters(String command)
    {
        if (parameters.containsKey(command))
            return parameters.get(command);
        return "(None Assigned)";
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void setDescription(String command, String description)
    {
        descriptions.put(command, description);
        FileManager.save(descriptions, "commands.descriptions");
    }

    public static String getDescription(String command)
    {
        if (descriptions.containsKey(command))
            return descriptions.get(command);
        return "(None Assigned)";
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static boolean isCommand(String command)
    {
        return classes.containsKey(command) && ranks.containsKey(command);
    }

    public static Set<String> getCommands()
    {
        return classes.keySet();
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void throwUnknownCommandError(PircBotX bot, User sender, String command)
    {
        bot.sendNotice(sender, String.format("Error: The command '%s' was not recognized!", command));
    }

    public static void throwInsufficientRankError(PircBotX bot, User sender, int p, int r)
    {
        bot.sendNotice(sender, String.format("Error: Insufficient permissions! (Your rank: %d Required rank: %s)", p, r));
    }

    public static void throwIncorrectParametersError(PircBotX bot, User sender, String command)
    {
        bot.sendNotice(sender, String.format("Error: Incorrect parameters! (Command usage: %s)", getParameters(command)));
    }

    public static void throwUnknownChannelError(PircBotX bot, User sender, String channel)
    {
        bot.sendNotice(sender, String.format("Error: The channel '%s' was not recognized!", channel));
    }

    public static void throwUnknownSettingError(PircBotX bot, User sender, String setting)
    {
        bot.sendNotice(sender, String.format("Error: The setting '%s' was not recognized!", setting));
    }

    public static void throwGenericError(PircBotX bot, User sender, String message)
    {
        bot.sendNotice(sender, message);
    }
}
