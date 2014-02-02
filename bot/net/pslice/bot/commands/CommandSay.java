package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandSay implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length > 1
                && args[0].startsWith("#"))
        {
            String message = args[1];
            for (int i = 2; i < args.length; i++)
                message += " " + args[i];

            boolean inChannel = false;

            if (bot.getChannel(args[0]).getUsers().contains(bot.getUserBot()))
                inChannel = true;

            if (!inChannel)
                bot.joinChannel(args[0]);

            bot.sendMessage(args[0], message);

            if (!inChannel)
                bot.partChannel(bot.getChannel(args[0]));
        }
        else if (args.length > 0)
        {
            String message = args[0];
            for (int i = 1; i < args.length; i++)
                message += " " + args[i];
            bot.sendMessage(channel, message);
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
