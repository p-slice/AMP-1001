package net.pslice.bot;

import net.pslice.bot.data.EventData;
import net.pslice.bot.data.MessageData;
import net.pslice.bot.data.PrivateMessageData;
import net.pslice.bot.source.FileReader;
import org.pircbotx.PircBotX;

public class AMP {

    private static final PircBotX AMP = new PircBotX();

    public static PircBotX getBot() {
        return AMP;
    }

    private static final String[] botInfo = FileReader.wholeText("properties.botinfo");

    public static void main(String[] args) {


        AMP.setName(botInfo[0]);
        AMP.setLogin(botInfo[1]);
        AMP.setVersion(botInfo[2]);

        AMP.setVerbose(true);
        AMP.setAutoNickChange(false);
        AMP.setAutoReconnect(true);
        AMP.setAutoReconnectChannels(true);

        AMP.setCapEnabled(true);

        AMP.getListenerManager().addListener(new MessageData());
        AMP.getListenerManager().addListener(new PrivateMessageData());
        AMP.getListenerManager().addListener(new EventData());

        joinServer();
    }

    private static void joinServer() {
        try {
            AMP.connect("irc.esper.net");
            String[] channels = FileReader.wholeText("properties.channels");
            for (String channel : channels)
                AMP.joinChannel(channel);
            AMP.identify(botInfo[4]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
