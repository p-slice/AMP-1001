package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandJoin implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 1)
        {
            if (args[0].startsWith("#"))
                bot.joinChannel(args[0]);
            else
                CommandManager.throwUnknownChannelError(bot, sender, args[0]);
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
