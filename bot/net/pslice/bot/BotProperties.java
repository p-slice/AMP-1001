package net.pslice.bot;

import net.pslice.bot.commands.*;

import java.util.HashMap;
import java.util.Properties;

public final class BotProperties {

    /*
     * ===========================================
     * Initializer:
     *
     * Private to ensure no pointless instances are created
     * ===========================================
     */

    private BotProperties(){}





    /**
     * ===========================================
     * Default properties for the bot:
     *
     * @return The default properties
     * These are used as defaults for loaded properties
     * If no loadable properties can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static Properties defaultProperties()
    {
        Properties defaultProperties = new Properties();

        // Nick used by the bot
        defaultProperties.put("nick", "AMP-1001");

        // Login used by the bot
        defaultProperties.put("login", "AMP-1001");

        // Real name used by the bot
        defaultProperties.put("realname", "A bot made by p_slice");

        // Is raw info sent to the console?
        defaultProperties.put("verbose", "true");

        // Should the bot change its nick on startup if it is already in use?
        defaultProperties.put("changenick", "false");

        // Who should have complete control over the bot
        defaultProperties.put("master", "p_slice");

        // What prefix should be used before commands
        defaultProperties.put("prefix", "+");

        // Server to connect to upon startup
        defaultProperties.put("server", "irc.esper.net");

        // Chanel to connect to upon startup
        defaultProperties.put("channels", "#p_slice");

        // Should the bot identify upon startup?
        defaultProperties.put("identify", "false");

        // NickServ name to log in with - UNUSED
        defaultProperties.put("nickservID", "");

        // NickServ password to log in with
        defaultProperties.put("nickservPASS", "");

        return defaultProperties;
    }





    /**
     * ===========================================
     * Default classes for commands:
     *
     * @return The default classes
     * If no loadable classes can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, Class<?>> defaultClasses()
    {
        HashMap<String, Class<?>> classes = new HashMap<>();

        // Listed in alphabetical order:

        classes.put("action", CommandAction.class);
        classes.put("alert", CommandAlert.class);
        classes.put("help", CommandHelp.class);
        classes.put("identify", CommandIdentify.class);
        classes.put("join", CommandJoin.class);
        classes.put("kick", CommandKick.class);
        classes.put("leave", CommandLeave.class);
        classes.put("link", CommandLink.class);
        classes.put("override", CommandOverride.class);
        classes.put("ping", CommandPing.class);
        classes.put("quit", CommandQuit.class);
        classes.put("reload", CommandReload.class);
        classes.put("rename", CommandRename.class);
        // classes.put("restart", CommandRestart.class);   <- This command is currently broken...
        classes.put("say", CommandSay.class);
        classes.put("setcommand", CommandSet.class);
        classes.put("setproperty", CommandProperties.class);
        classes.put("setuser", CommandSetUser.class);
        classes.put("solve", CommandSolve.class);
        classes.put("toggleinputmethod", CommandInput.class);
        classes.put("twerk", CommandTwerk.class);
        classes.put("user", CommandUser.class);
        classes.put("verbose", CommandVerbose.class);

        return classes;
    }





    /**
     * ===========================================
     * Default ranks for commands:
     *
     * @return Default ranks
     * If no loadable ranks can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, Integer> defaultRanks()
    {
        HashMap<String, Integer> ranks = new HashMap<>();

        // Listed in order of rank:

        ranks.put("help", 0);
        ranks.put("ping", 0);
        ranks.put("solve", 0);
        ranks.put("twerk", 0);
        ranks.put("user", 0);
        ranks.put("link", 5);
        ranks.put("action", 7);
        ranks.put("identify", 7);
        ranks.put("join", 7);
        ranks.put("leave", 7);
        ranks.put("reload", 7);
        ranks.put("restart", 7);
        ranks.put("say", 7);
        ranks.put("toggleinputmethod", 7);
        ranks.put("verbose", 7);
        ranks.put("alert", 10);
        ranks.put("kick", 10);
        ranks.put("override", 10);
        ranks.put("quit", 10);
        ranks.put("rename", 10);
        ranks.put("setcommand", 10);
        ranks.put("setproperty", 10);
        ranks.put("setuser", 10);

        return ranks;
    }





    /**
     * ===========================================
     * Default parameters for commands:
     *
     * @return Default parameters
     * If no loadable parameters can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, String> defaultParameters()
    {
        HashMap<String, String> parameters = new HashMap<>();

        // Listed in alphabetical order:

        parameters.put("action", "action (#channel) <activity>");
        parameters.put("alert", "alert <name>");
        parameters.put("help", "help (command)");
        parameters.put("identify", "identify");
        parameters.put("join", "join <#channel>");
        parameters.put("kick", "kick <user> (reason)");
        parameters.put("leave", "leave (#channel)");
        parameters.put("link", "link <name> (new link)");
        parameters.put("override", "override");
        parameters.put("ping", "ping");
        parameters.put("quit", "quit");
        parameters.put("reload", "reload");
        parameters.put("rename", "rename <name>");
        parameters.put("restart", "restart");
        parameters.put("say", "say (#channel) <message>");
        parameters.put("setcommand", "setcommand <setting> <command> <value>");
        parameters.put("setproperty", "setproperty <property> <value>");
        parameters.put("setuser", "setuser <setting> <user> <value>");
        parameters.put("solve", "solve <equation>");
        parameters.put("toggleinputmethod", "+toggleinputmethod");
        parameters.put("twerk", "twerk");
        parameters.put("user", "user (setting) <user>");
        parameters.put("verbose", "verbose");

        return parameters;
    }





    /**
     * ===========================================
     * Default descriptions for commands:
     *
     * @return Default descriptions
     * If no loadable descriptions can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, String> defaultDescriptions()
    {
        HashMap<String, String> descriptions = new HashMap<>();

        // Listed in alphabetical order

        descriptions.put("action", "Send an action to a channel");
        descriptions.put("alert", "Toggle alerts for server events");
        descriptions.put("help", "Get info about commands");
        descriptions.put("identify", "Log in with NickServ");
        descriptions.put("join", "Join a channel");
        descriptions.put("kick", "Kick a player from the channel");
        descriptions.put("leave", "Leave a channel");
        descriptions.put("link", "Save and load links to websites");
        descriptions.put("override", "Toggle Override mode");
        descriptions.put("ping", "Check if the bot is alive");
        descriptions.put("quit", "Leave the server");
        descriptions.put("reload", "Reload all bot files");
        descriptions.put("rename", "Change the name of the bot");
        descriptions.put("restart", "Restart the bot (Currently broken)");
        descriptions.put("say", "Send a message to a channel");
        descriptions.put("setcommand", "Change the setting of a command");
        descriptions.put("setproperty", "Set a property of the bot");
        descriptions.put("setuser", "Change the setting of a user");
        descriptions.put("solve", "Solve a simple equation");
        descriptions.put("toggleinputmethod", "Toggle input method");
        descriptions.put("twerk", "Test your twerking skills");
        descriptions.put("user", "Get info about a user");
        descriptions.put("verbose", "Toggle verbosity to the console");

        return descriptions;
    }

    /*
     * ===========================================
     * Default alert statuses:
     *
     * If no loadable statuses can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, Boolean> defaultAlerts()
    {
        HashMap<String, Boolean> alerts = new HashMap<>();

        // Listed in alphabetical order

        alerts.put("afk", true);
        alerts.put("invite", true);
        alerts.put("join", true);
        alerts.put("leave", true);
        alerts.put("kick", true);
        alerts.put("nickchange", false);
        alerts.put("op", false);
        alerts.put("settopic", false);
        alerts.put("voice", false);

        return alerts;
    }
}

