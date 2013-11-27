package net.pslice.bot.data;

import net.pslice.bot.AMP;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class PrivateMessageData extends ListenerAdapter {

    private static final PircBotX bot = AMP.getBot();

    public void onPrivateMessage(PrivateMessageEvent event) {
        User user = event.getUser();

        bot.sendNotice(user, "I am nothing but a bot, you can't simply PM me! However, I've passed your message on to my Master, p_slice.");
        bot.sendNotice("p_slice", "A message to me from " + user.getNick() + ": " + event.getMessage());
    }
}