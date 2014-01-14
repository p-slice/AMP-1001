package net.pslice.bot.commands;

import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.FileManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashMap;

public class CommandLink implements Command {

    private static HashMap<String, String> links;

    public static void loadFiles()
    {
        if (FileManager.fileExists("links"))
            links = FileManager.load("links");
        else
        {
            links = new HashMap<>();
            FileManager.save(links, "links");
        }
    }

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args)
    {
        if (args.length == 1)
        {
            String link = args[0].toLowerCase();
            if (links.containsKey(link))
                bot.sendMessage(channel, String.format("Link: %s", links.get(link)));
            else
                CommandManager.throwGenericError(bot, sender, String.format("Error: The link '%s' was not recognized!", link));
        }
        else if (args.length == 2)
        {
            String link = args[0].toLowerCase();
            links.put(link, args[1]);
            FileManager.save(links, "links");
            bot.sendMessage(channel, "Link saved.");
        }
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
