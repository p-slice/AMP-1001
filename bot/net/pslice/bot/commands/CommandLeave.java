package net.pslice.bot.commands;

import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public final class CommandLeave implements Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
            bot.partChannel(channel, String.format("Leave command by '%s'", sender.getNick()));
        else if (args.length == 1)
        {
            if (args[0].startsWith("#"))
                bot.partChannel(bot.getChannel(args[0]), String.format("Leave command by '%s'", sender.getNick()));
            else
                CommandManager.throwUnknownChannelError(bot, sender, args[0]);
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
