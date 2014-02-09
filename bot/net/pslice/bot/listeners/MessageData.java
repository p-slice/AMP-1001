package net.pslice.bot.listeners;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.PropertiesManager;
import net.pslice.bot.managers.UserManager;
import net.pslice.bot.commands.Command;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public final class MessageData extends ListenerAdapter<AmpBot> {

    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The message event that occurred
     * This method listens for messages sent directly to
     *     a channel
     * If the message starts with the prefix assigned for
     *     commands, the bot passes the info on the the
     *     master command executor
     * ===========================================
     */

    public void onMessage(MessageEvent<AmpBot> event) {
        AmpBot bot = event.getBot();

        Channel channel = event.getChannel();
        User user = event.getUser();

        String[] messageSplit = event.getMessage().split("[ ]");

        if (messageSplit[0].startsWith(bot.getPropertiesManager().getProperty("prefix"))
                && !messageSplit[0].equals(bot.getPropertiesManager().getProperty("prefix")))
            doCommand(bot, channel, user, messageSplit);
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The private message event that occurred
     * This method listens for private messages sent to the bot
     * If the message starts with the prefix assigned for
     *     commands, the bot passes the info on the the
     *     master command executor
     * Otherwise, the bot sends a message to the bot Master with
     *     the contents of the message it recieved
     * ===========================================
     */

    public void onPrivateMessage(PrivateMessageEvent<AmpBot> event)
    {
        AmpBot bot = event.getBot();
        User user = event.getUser();
        String message = event.getMessage();

        String[] messageSplit = event.getMessage().split("[ ]");

        if (messageSplit[0].startsWith(bot.getPropertiesManager().getProperty("prefix"))
                && !messageSplit[0].equals(bot.getPropertiesManager().getProperty("prefix")))
        {
            doCommand(bot, bot.getChannel(user.getNick()), user, messageSplit);
            if (!user.equals(bot.getUser(bot.getPropertiesManager().getProperty("master"))))
                bot.sendNotice(bot.getPropertiesManager().getProperty("master"), String.format("Private command run by '%s': %s", user.getNick(), message));
        }

        else if (!user.equals(bot.getUser(bot.getPropertiesManager().getProperty("master"))))
        {
            bot.sendMessage(bot.getPropertiesManager().getProperty("master"), String.format("Message from '%s': %s", user.getNick(), message));
            bot.sendMessage(user, "Your message has been forwarded to my master.");
        }
    }





    /**
     * ===========================================
     * Static method to prepare a command for execution:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param user: The user the command was sent by
     * @param messageSplit: An array of the command, split into individual words
     * This method checks for 4 things before executing the command:
     *  1) The state of override. If override is enabled,
     *      only the bot Master is allowed to perform commands
     *  2) If the command exists. An error occurs if it doesn't
     *  3) If the command is enabled. The user is notified if it isn't
     *  4) If the user has a high enough rank to execute
     *      the command. Ranks are based either on defined
     *      ranks or channel ranks, based on bot settings
     *     The override command does not require any rank as it
     *        uses its own requirements
     * If all 4 requirements are met, the method attempts to
     *   create a Command object and execute it
     * The bot Master is capable of enabling override mode at any time
     *   This is useful if he/she accidentally sets his/her rank
     *      too low and is incapable of regaining it. In override
     *      mode, the bot Master can run any command without regard
     *      for rank
     * ===========================================
     */

    private static void doCommand(AmpBot bot, Channel channel, User user, String[] messageSplit)
    {
        UserManager userManager = bot.getUserManager();
        PropertiesManager propertiesManager = bot.getPropertiesManager();
        CommandManager commandManager = bot.getCommandManager();

        String command = messageSplit[0].replace(propertiesManager.getProperty("prefix"), "");
        String[] args = new String[messageSplit.length - 1];
        System.arraycopy(messageSplit, 1, args, 0, messageSplit.length - 1);

        if (bot.isOverride()
                && !user.getNick().equals(propertiesManager.getProperty("master")))
        {
            CommandManager.throwGenericError(bot, user, "Override mode is activated, you do not have permission to do that.");
            return;
        }

        int p;
        if (!bot.usesChannelRanks())
            p = userManager.getRank(user.getNick());
        else
        {
            if (channel.isOp(user))
                p = 10;
            else if (channel.hasVoice(user))
                p = 5;
            else
                p = 0;
        }

        if (commandManager.isCommand(command))
        {
            Command cmd = commandManager.getCommand(command);
            if (cmd.isEnabled()
                    || bot.isOverride())
            {
                if (p >= cmd.getRank()
                        || bot.isOverride()
                        || command.equals("override"))
                    try
                    {
                        cmd.execute(bot, channel, user, args);
                    }

                    catch (Exception e) {
                        e.printStackTrace();
                    }

                else
                    CommandManager.throwInsufficientRankError(bot, user, p, cmd.getRank());
            }

            else
                CommandManager.throwGenericError(bot, user, String.format("The command '%s' is not currently enabled", command));
        }

        else {
            CommandManager.throwUnknownCommandError(bot, user, command);
        }
    }
}
