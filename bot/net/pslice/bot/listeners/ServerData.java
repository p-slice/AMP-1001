package net.pslice.bot.listeners;

import net.pslice.bot.AmpBot;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.NickChangeEvent;

public final class ServerData extends ListenerAdapter<AmpBot> {

    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The nick change event that occurred
     * This method listens for a user changing their name
     * There are two alerts associated with this event:
     *  1) If the generic alert is enabled, the bot Master
     *       is sent a notice with the old and new names of the user
     *  2) If the AFK alert, the new and old names are matched
     *       with variations of AFK name endings. If the user
     *       appears to have either gone AFK or returned, the
     *       bot Master is sent a notice of the status of the
     *       user.
     * ===========================================
     */

    public void onNickChange(NickChangeEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");

        if (event.getBot().getPropertiesManager().getAlertState("nickchange")
                && !event.getUser().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s is now known as %s", event.getOldNick(), event.getNewNick()));


        String oldNick = event.getOldNick().toLowerCase();
        String newNick = event.getNewNick().toLowerCase();

        if (event.getBot().getPropertiesManager().getAlertState("afk")
                && !event.getUser().equals(event.getBot().getUserBot()))
        {
            // Check if the name no longer has an AFK-related ending
            if ((oldNick.endsWith("|afk")
                    || oldNick.endsWith("|away")
                    || oldNick.endsWith("|sleeping")
                    || oldNick.endsWith("|asleep"))
                    && !newNick.endsWith("|afk")
                    && !newNick.endsWith("|away")
                    && !newNick.endsWith("|sleeping")
                    && !newNick.endsWith("|asleep"))
                event.getBot().sendMessage(master, String.format("Notice: %s is no longer afk!", event.getNewNick()));

            // Check if the name now has an AFK-related ending
            if ((newNick.endsWith("|afk")
                    || newNick.endsWith("|away")
                    || newNick.endsWith("|sleeping")
                    || newNick.endsWith("|asleep"))
                    && !oldNick.endsWith("|afk")
                    && !oldNick.endsWith("|away")
                    && !oldNick.endsWith("|sleeping")
                    && !oldNick.endsWith("|asleep"))
                event.getBot().sendMessage(master, String.format("Notice: %s is now afk!", event.getOldNick()));
        }
    }
}
