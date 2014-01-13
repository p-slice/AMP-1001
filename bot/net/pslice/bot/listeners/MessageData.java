package net.pslice.bot.listeners;

import net.pslice.bot.AmpBot;
import net.pslice.bot.BotProperties;
import net.pslice.bot.commands.Command;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.lang.reflect.Constructor;

public final class MessageData extends ListenerAdapter<AmpBot> {

    public void onMessage(MessageEvent<AmpBot> event) {
        PircBotX bot = event.getBot();

        Channel chan = event.getChannel();
        User user = event.getUser();

        String[] messageSplit = event.getMessage().split("[ ]");

        if (messageSplit[0].startsWith(AmpBot.getProperty("prefix")))
        {
            String command = messageSplit[0].replace(AmpBot.getProperty("prefix"), "");
            String[] args = new String[messageSplit.length - 1];
            System.arraycopy(messageSplit, 1, args, 0, messageSplit.length - 1);

            if (AmpBot.isOverride()
                    && !user.getNick().equals(AmpBot.getProperty("master")))
            {
                CommandManager.throwGenericError(bot, user, "Override mode is activated, you do not have permission to do that.");
                return;
            }

            int p;
            if (!AmpBot.getInputType())
                p = UserManager.getRank(user.getNick());
            else
            {
                if (chan.isOp(user))
                    p = 10;
                else if (chan.hasVoice(user))
                    p = 5;
                else
                    p = 0;
            }

            int r = CommandManager.getRank(command);

            if (CommandManager.isCommand(command))
            {
                if (p >= r)
                    try
                    {
                    Command cmd = (Command)CommandManager.getClass(command).newInstance();
                    cmd.execute(bot, chan, user, command, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else
                    CommandManager.throwInsufficientRankError(bot, user, p, r);
            } else {
                CommandManager.throwUnknownCommandError(bot, user, command);
            }
        }
    }
}
