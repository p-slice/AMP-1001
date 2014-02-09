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

        // Channels to connect to upon startup
        defaultProperties.put("channels", "#p_slice");

        // Should the bot identify upon startup?
        defaultProperties.put("identify", "false");

        // NickServ name to log in with - UNUSED
        defaultProperties.put("nickservID", "");

        // NickServ password to log in with
        defaultProperties.put("nickservPASS", "");

        return defaultProperties;
    }





    /*
     * ===========================================
     * Default alert statuses:
     *
     * If no loadable statuses can be found, these get
     *     saved in their place
     * ===========================================
     */

    public static HashMap<String, Command> defaultCommands()
    {
        HashMap<String, Command> commands = new HashMap<>();

        commands.put("action", new CommandAction());
        commands.put("alert", new CommandAlert());
        commands.put("function", new CommandFunction());
        commands.put("help", new CommandHelp());
        commands.put("identify", new CommandIdentify());
        commands.put("info", new CommandInfo());
        commands.put("input", new CommandInput());
        commands.put("join", new CommandJoin());
        commands.put("kick", new CommandKick());
        commands.put("leave", new CommandLeave());
        commands.put("override", new CommandOverride());
        commands.put("ping", new CommandPing());
        commands.put("quit", new CommandQuit());
        commands.put("reload", new CommandReload());
        commands.put("remember", new CommandRemember());
        commands.put("rename", new CommandRename());
        commands.put("reset", new CommandReset());
        commands.put("restart", new CommandRestart());
        commands.put("say", new CommandSay());
        commands.put("setcommand", new CommandSet());
        commands.put("setproperty", new CommandSetProperty());
        commands.put("setuser", new CommandSetUser());
        commands.put("solve", new CommandSolve());
        commands.put("twerk", new CommandTwerk());
        commands.put("user", new CommandUser());
        commands.put("verbose", new CommandVerbose());

        return commands;
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

