package net.pslice.bot.listeners;

import net.pslice.bot.AmpBot;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public final class PrivateMessageData extends ListenerAdapter<AmpBot> {

    public void onPrivateMessage(PrivateMessageEvent<AmpBot> event)
    {
        PircBotX bot = event.getBot();
        User user = event.getUser();
        String message = event.getMessage();

        if (!user.equals(bot.getUser(AmpBot.getProperty("master"))))
        {
            bot.sendMessage(AmpBot.getProperty("master"), String.format("Message from '%s': %s", user.getNick(), message));
            bot.sendMessage(user, "Your message has been forwarded to my master.");
        }
    }
}
