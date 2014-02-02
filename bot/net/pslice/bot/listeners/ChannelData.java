package net.pslice.bot.listeners;

import net.pslice.bot.AmpBot;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

public final class ChannelData extends ListenerAdapter<AmpBot> {

    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The join event that occurred
     * This method listens for a user joining a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onJoin(JoinEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("join")
                && !event.getUser().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s has joined the channel %s", event.getUser().getNick(), event.getChannel().getName()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The part/leave event that occurred
     * This method listens for a user leaving a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onPart(PartEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("leave")
                && !event.getUser().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s has left the channel %s", event.getUser().getNick(), event.getChannel().getName()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The topic event that occurred
     * This method listens for the topic of a channel being changed
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onTopic(TopicEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("topic")
                && !event.getUser().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s has changed the topic of the channel %s", event.getUser().getNick(), event.getChannel().getName()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The invite event that occurred
     * This method listens for a user inviting the bot to a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onInvite(InviteEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("invite"))
            event.getBot().sendMessage(master, String.format("Notice: %s has invited me to the channel %s", event.getUser(), event.getChannel()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The kick event that occurred
     * This method listens for a user getting kicked from a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onKick(KickEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("kick")
                && !event.getRecipient().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s has been kicked from the channel %s", event.getRecipient().getNick(), event.getChannel().getName()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The Op event that occurred
     * This method listens for a user getting Op in a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onOp(OpEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("op")
                && !event.getRecipient().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s in now OP in the channel %s", event.getRecipient().getNick(), event.getChannel().getName()));
    }





    /**
     * ===========================================
     * Method inherited from the ListenerAdapter class
     *
     * @param event: The voice event that occurred
     * This method listens for a user getting voiced in a channel
     * If the alert for this is turned on, the bot Master
     *      is sent a notice with the user and channel
     * ===========================================
     */

    public void onVoice(VoiceEvent<AmpBot> event)
    {
        String master = event.getBot().getPropertiesManager().getProperty("master");
        if (event.getBot().getPropertiesManager().getAlertState("voice")
                && !event.getRecipient().equals(event.getBot().getUserBot()))
            event.getBot().sendMessage(master, String.format("Notice: %s has joined the channel %s", event.getRecipient().getNick(), event.getChannel().getName()));
    }
}
