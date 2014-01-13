package net.pslice.bot.commands;

import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public final class CommandUser implements Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 1)
        {
            bot.sendMessage(channel, String.format("[Info for '%s'] Rank: %d Nickname: %s", args[0], UserManager.getRank(args[0]), UserManager.getNick(args[0])));
        }
        else if (args.length == 2)
        {
            String setting = args[0].toLowerCase();
            switch (setting) {
                case "rank":
                    bot.sendMessage(channel, String.format("%s's assigned rank: %d", args[1], UserManager.getRank(args[1])));
                    break;
                case "nick":
                    bot.sendMessage(channel, String.format("%s's assigned nick: %s", args[1], UserManager.getNick(args[1])));
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
