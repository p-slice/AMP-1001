package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandSetUser implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length >= 3)
        {
            UserManager userManager = bot.getUserManager();

            String setting = args[0].toLowerCase();
            String user = args[1];
            switch (setting) {
                case "rank":
                    if (args[2].matches("-?[\\d]+")) {
                        userManager.setRank(user, Integer.parseInt(args[2]));
                        bot.sendMessage(channel, String.format("The rank for '%s' is now %s", user, args[2]));
                    } else
                        CommandManager.throwGenericError(bot, sender, "Error: Rank must be a number!");
                    break;
                case "nickname":
                case "nick":
                    String nick = args[2];
                    for (int i = 3; i < args.length; i++)
                        nick += " " + args[i];
                    userManager.setNick(user, nick);
                    bot.sendMessage(channel, String.format("The nick for '%s' is now '%s'", user, nick));
                    break;
                default:
                    CommandManager.throwUnknownSettingError(bot, sender, setting);
                    break;
            }
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
