package net.pslice.bot.data;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class EventData extends ListenerAdapter {

    private static final PircBotX bot = AMP.getBot();

    public void onJoin(JoinEvent event) {
        Channel chan = event.getChannel();
        User user = event.getUser();

        if (chan.getName().equals("#p_slice") && !user.getNick().equals(bot.getNick())){
            bot.sendNotice(user, "Welcome to \u00033#p_slice\u00031, " + user.getNick());
            bot.sendNotice(user, "My name is AMP-1001 and I'll be your host during your stay in this channel.");
            bot.sendNotice(user, "If you have any questions, you can talk to my master, \u00033p_slice\u00031, if he's around.");
            bot.sendNotice(user, "You can also say '\u00033+commands\u00031' for a list of my current abilities.");

            BotUser userInfo = new BotUser(user.getNick());

            if (userInfo.getRank() >= 5)
                bot.voice(chan, user);
        }
    }
}
