package net.pslice.bot.data;

import net.pslice.bot.commands.Command;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class MessageData extends ListenerAdapter {

    private static String message;

    public static String getMessage() {
        return message;
    }

    public void onMessage(MessageEvent event) throws Exception {
        Channel chan = event.getChannel();
        User user = event.getUser();

        message = event.getMessage();
        String[] messageSplit = message.split("[ ]");

        if (message.startsWith("+")) {
            Command.runCommand(chan, user, messageSplit);
        }
        boolean containsLanguage = AdminData.checkLanguage(message);
        if (containsLanguage && chan.getName().equals("#p_slice")) {
            AdminData.languageKick(chan, user);
        }
    }
}
