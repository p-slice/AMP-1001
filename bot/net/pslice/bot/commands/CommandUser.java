package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandUser extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandUser()
    {
        super("user", 0, "<name> (setting)", "Get info about a user", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command returns info about a specified user
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        UserManager userManager = bot.getUserManager();

        // One argument - the name of the user - return all info
        if (args.length == 1)
            bot.sendMessage(channel, String.format("[Info for '%s'] Rank: %d Nickname: %s", args[0], userManager.getRank(args[0]), userManager.getNick(args[0])));

            // Two arguments - the name of the user and the setting
        else if (args.length == 2)
        {
            String setting = args[0].toLowerCase();

            // Check which setting is being queried
            switch (setting)
            {
                // Get the rank of the user
                case "rank":
                case "r":
                    bot.sendMessage(channel, String.format("%s's assigned rank: %d", args[1], userManager.getRank(args[1])));
                    break;

                // Get the nickname of the user
                case "nickname":
                case "nick":
                case "n":
                    bot.sendMessage(channel, String.format("%s's assigned nick: %s", args[1], userManager.getNick(args[1])));
                    break;

                // The setting was not recognized - throw an error
                default:
                    CommandManager.throwUnknownSettingError(bot, sender, setting);
                    break;
            }
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
