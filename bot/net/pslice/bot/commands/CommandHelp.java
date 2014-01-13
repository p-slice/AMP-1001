package net.pslice.bot.commands;

import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public final class CommandHelp implements Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 0)
        {
            bot.sendNotice(sender, "List of Commands:");
            bot.sendNotice(sender, "(Use \u00034+help <command>\u00031 for specific command help)");
            for (String helpCommand : CommandManager.getCommands())
                bot.sendNotice(sender, String.format("\u0002%s\u000F: %s", helpCommand, CommandManager.getDescription(helpCommand)));
        }
        else if (args.length == 1)
        {
            String helpCommand = args[0].toLowerCase();
            if (CommandManager.isCommand(helpCommand))
            {
                bot.sendNotice(sender, String.format("Help for command '\u00034%s\u00031':", helpCommand));
                bot.sendNotice(sender, String.format("\u0002Required rank\u000F: %d", CommandManager.getRank(helpCommand)));
                bot.sendNotice(sender, String.format("\u0002Parameters\u000F: %s", CommandManager.getParameters(helpCommand)));
                bot.sendNotice(sender, String.format("\u0002Usage\u000F: %s", CommandManager.getDescription(helpCommand)));

            }
            else
                CommandManager.throwUnknownCommandError(bot, sender, helpCommand);
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
