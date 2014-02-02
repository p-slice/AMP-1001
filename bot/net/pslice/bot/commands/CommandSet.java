package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandSet implements Command {

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length >= 3)
        {
            CommandManager commandManager = bot.getCommandManager();

            String setting = args[0].toLowerCase();
            String editCommand = args[1].toLowerCase();

            if (commandManager.isCommand(editCommand))
            {
                switch (setting) {
                    case "rank":
                        if (args[2].matches("-?[\\d]+")) {
                            commandManager.setRank(editCommand, Integer.parseInt(args[2]));
                            bot.sendMessage(channel, String.format("The required rank for '%s' is now %s", editCommand, args[2]));
                        } else
                            CommandManager.throwGenericError(bot, sender, "Error: Rank must be a number!");
                        break;
                    case "parameters":
                    case "params":
                        String params = args[2];
                        for (int i = 3; i < args.length; i++)
                            params += " " + args[i];
                        commandManager.setParameters(editCommand, params);
                        bot.sendMessage(channel, String.format("The parameters for '%s' are now '%s'", editCommand, params));
                        break;
                    case "description":
                    case "desc":
                        String desc = args[2];
                        for (int i = 3; i < args.length; i++)
                            desc += " " + args[i];
                        commandManager.setDescription(editCommand, desc);
                        bot.sendMessage(channel, String.format("The description for '%s' is now '%s'", editCommand, desc));
                        break;
                    default:
                        CommandManager.throwUnknownSettingError(bot, sender, setting);
                        break;
                }
            }
            else
                CommandManager.throwUnknownCommandError(bot, sender, command);
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
