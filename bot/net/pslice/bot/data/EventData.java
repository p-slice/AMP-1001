package net.pslice.bot.data;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotUser;
import net.pslice.bot.source.FileReader;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;

import java.util.Arrays;

public class EventData extends ListenerAdapter {

    private static final PircBotX bot = AMP.getBot();

    public void onJoin(JoinEvent event) {
        Channel chan = event.getChannel();
        User user = event.getUser();

        if (chan.getName().equals("#p_slice") && !user.getNick().equals(bot.getNick())) {
            bot.sendNotice(user, "Welcome to \u00033#p_slice\u00031, " + user.getNick() + ". If you have any questions, talk to my Master, \u00033p_slice\u00031.");

            BotUser userInfo = new BotUser(user.getNick());

            if (userInfo.getRank() >= 5)
                bot.voice(chan, user);
        }
    }

    public void onKick(KickEvent event) {
        Channel chan = event.getChannel();
        User user = event.getRecipient();

        if (Arrays.toString(FileReader.wholeText("properties.channels")).contains(chan.getName()) && user.getNick().equals(bot.getNick())) {
            bot.joinChannel(chan.getName());
        }
    }
}
