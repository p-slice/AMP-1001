package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandIdentify implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            String pass = bot.getPropertiesManager().getProperty("nickservPASS");
            System.out.println(pass);
            if (!pass.isEmpty() && !pass.equals(""))
            {
                bot.identify(pass);
                if (!bot.getUserBot().isVerified())
                    CommandManager.throwGenericError(bot, sender, "Error: Couldn't identify (Incorrect password supplied?)");
            }
            else
                CommandManager.throwGenericError(bot, sender, "Error: No password was supplied to log in with!");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
