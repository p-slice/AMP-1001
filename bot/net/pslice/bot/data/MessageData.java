package net.pslice.bot.data;

import net.pslice.bot.commands.Command;
import net.pslice.bot.commands.CommandKick;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class MessageData extends ListenerAdapter {

    public void onMessage(MessageEvent event) throws Exception {
        Channel chan = event.getChannel();
        User user = event.getUser();

        String message = event.getMessage();
        String[] messageSplit = message.split("[ ]");

        if (message.startsWith("+")) {
            Command.runCommand(chan, user, messageSplit);
        }
        boolean containsLanguage = AdminData.checkLanguage(message);
        if (containsLanguage) {
            CommandKick.languageKick(chan, user);
        }
    }
}
