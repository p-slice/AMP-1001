package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandQuit implements Command{

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
            bot.quitServer(String.format("Disconnect command by user '%s'", sender.getNick()));
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
