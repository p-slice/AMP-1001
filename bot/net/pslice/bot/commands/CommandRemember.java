package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandRemember extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandRemember()
    {
        super("remember", 7, "<name> (new memory)", "Save or recall a memory", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will save and return text from a file
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        PropertiesManager propertiesManager = bot.getPropertiesManager();

        // One argument - recall a memory
        if (args.length == 1)
            bot.sendMessage(channel, propertiesManager.recall(args[0].toLowerCase()));

        // Multiple arguments - save a memory
        else if (args.length > 1)
        {
            String memory = args[0].toLowerCase();
            String memoryS = args[1];
            for (int i = 2; i < args.length; i++)
                memoryS += " " + args[i];

            propertiesManager.remember(memory, memoryS);
            bot.sendMessage(channel, "Saved to memory.");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
