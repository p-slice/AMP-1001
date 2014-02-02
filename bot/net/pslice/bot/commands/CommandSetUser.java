package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandSetUser implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command will change the settings of a specified user
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Command requires three arguments - The setting, the name of the user, and the new value
        if (args.length >= 3)
        {
            UserManager userManager = bot.getUserManager();

            String setting = args[0].toLowerCase();
            String user = args[1];

            // Check which setting is being changed
            switch (setting)
            {

                // Change the rank of the user
                case "rank":
                case "r":

                    // Make sure the new rank is numbers only
                    if (args[2].matches("-?[\\d]+")) {
                        userManager.setRank(user, Integer.parseInt(args[2]));
                        bot.sendMessage(channel, String.format("The rank for '%s' is now %s", user, args[2]));
                    }

                    // Throw an error if it isn't
                    else
                        CommandManager.throwGenericError(bot, sender, "Error: Rank must be a number!");
                    break;

                // Change the nickname of the user
                case "nickname":
                case "nick":
                case "n":
                    String nick = args[2];
                    for (int i = 3; i < args.length; i++)
                        nick += " " + args[i];
                    userManager.setNick(user, nick);
                    bot.sendMessage(channel, String.format("The nick for '%s' is now '%s'", user, nick));
                    break;

                // The setting is not recognized - throw an error
                default:
                    CommandManager.throwUnknownSettingError(bot, sender, setting);
                    break;
            }
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
