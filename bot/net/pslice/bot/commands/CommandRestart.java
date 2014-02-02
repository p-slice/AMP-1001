package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandRestart implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            try
            {
                bot.reconnect();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
