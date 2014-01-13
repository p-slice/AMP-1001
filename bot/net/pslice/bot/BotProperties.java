package net.pslice.bot;

import net.pslice.bot.commands.*;

import java.util.HashMap;
import java.util.Properties;

public final class BotProperties {

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private BotProperties(){}

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static Properties defaultProperties()
    {
        Properties defaultProperties = new Properties();
        defaultProperties.put("nick", "AMP-1001");
        defaultProperties.put("login", "AMP-1001");
        defaultProperties.put("realname", "A bot made by p_slice");
        defaultProperties.put("verbose", "true");
        defaultProperties.put("changenick", "false");
        defaultProperties.put("master", "p_slice");
        defaultProperties.put("prefix", "+");
        defaultProperties.put("server", "irc.esper.net");
        defaultProperties.put("channel", "#p_slice");
        defaultProperties.put("identify", "false");
        defaultProperties.put("nickservID", "");
        defaultProperties.put("nickservPASS", "");

        return defaultProperties;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static HashMap<String, Class<?>> defaultClasses()
    {
        HashMap<String, Class<?>> classes = new HashMap<>();

        classes.put("help", CommandHelp.class);
        classes.put("quit", CommandQuit.class);
        classes.put("join", CommandJoin.class);
        classes.put("leave", CommandLeave.class);
        classes.put("say", CommandSay.class);
        classes.put("setcommand", CommandSet.class);
        classes.put("setuser", CommandSetUser.class);
        classes.put("kick", CommandKick.class);
        classes.put("user", CommandUser.class);
        classes.put("rename", CommandRename.class);
        classes.put("verbose", CommandVerbose.class);
        classes.put("override", CommandOverride.class);
        classes.put("toggleinputmethod", CommandInput.class);
        classes.put("setproperty", CommandProperties.class);
        classes.put("reload", CommandReload.class);
        classes.put("identify", CommandIdentify.class);

        return classes;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static HashMap<String, Integer> defaultRanks()
    {
        HashMap<String, Integer> ranks = new HashMap<>();

        ranks.put("help", 0);
        ranks.put("quit", 10);
        ranks.put("join", 10);
        ranks.put("leave", 10);
        ranks.put("say", 10);
        ranks.put("setcommand", 10);
        ranks.put("setuser", 10);
        ranks.put("kick", 10);
        ranks.put("user", 3);
        ranks.put("rename", 10);
        ranks.put("verbose", 7);
        ranks.put("override", 0);
        ranks.put("toggleinputmethod", 7);
        ranks.put("setproperty", 10);
        ranks.put("reload", 7);
        ranks.put("identify", 7);

        return ranks;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static HashMap<String, String> defaultParameters()
    {
        HashMap<String, String> parameters = new HashMap<>();

        String PREFIX = AmpBot.getProperty("prefix");

        parameters.put("help", PREFIX + "help (command)");
        parameters.put("quit", PREFIX + "quit");
        parameters.put("join", PREFIX + "join <#channel>");
        parameters.put("leave", PREFIX + "leave (#channel)");
        parameters.put("say", PREFIX + "say (#channel) <message>");
        parameters.put("setcommand", PREFIX + "setcommand <setting> <command> <value>");
        parameters.put("setuser", PREFIX + "setuser <setting> <user> <value>");
        parameters.put("kick", PREFIX + "kick <user> (reason)");
        parameters.put("user", PREFIX + "user (setting) <user>");
        parameters.put("rename", PREFIX + "rename <name>");
        parameters.put("verbose", PREFIX + "verbose");
        parameters.put("override", PREFIX + "override");
        parameters.put("toggleinputmethod", PREFIX + "+toggleinputmethod");
        parameters.put("setproperty", PREFIX + "setproperty <property> <value>");
        parameters.put("reload", PREFIX + "reload");
        parameters.put("identify", PREFIX + "identify");

        return parameters;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static HashMap<String, String> defaultDescriptions()
    {
        HashMap<String, String> descriptions = new HashMap<>();

        descriptions.put("help", "Get info about commands");
        descriptions.put("quit", "Leave the server");
        descriptions.put("join", "Join a channel");
        descriptions.put("leave", "Leave a channel");
        descriptions.put("say", "Send a message to a channel");
        descriptions.put("setcommand", "Change the setting of a command");
        descriptions.put("setuser", "Change the setting of a user");
        descriptions.put("kick", "Kick a player from the channel");
        descriptions.put("user", "Get info about a user");
        descriptions.put("rename", "Change the name of the bot");
        descriptions.put("verbose", "Toggle verbosity to the console");
        descriptions.put("override", "Toggle Override mode");
        descriptions.put("toggleinputmethod", "Toggle input method");
        descriptions.put("setproperty", "Set a property of the bot");
        descriptions.put("reload", "Reload all bot files");
        descriptions.put("identify", "Log in with NickServ");

        return descriptions;
    }
}

