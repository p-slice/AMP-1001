package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandInput implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            bot.toggleRankInput();
            if (bot.usesChannelRanks())
                bot.sendMessage(channel, "Input method now based on channel permissions.");
            else
                bot.sendMessage(channel, "Input method now based on assigned ranks");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
