package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandReload implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            bot.loadAllFiles();
            bot.sendMessage(channel, "All files reloaded!");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
