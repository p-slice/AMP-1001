package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandRename implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 1)
            bot.changeNick(args[0]);
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
