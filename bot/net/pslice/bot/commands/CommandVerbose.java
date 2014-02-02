package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandVerbose implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            bot.setVerbose(!bot.isVerbose());
            if (bot.isVerbose())
                bot.sendMessage(channel, "Verbose now turned on.");
            else
                bot.sendMessage(channel, "Verbose now turned off.");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
