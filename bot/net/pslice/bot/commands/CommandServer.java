package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

class CommandServer {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {
        String server = messageSplit[2];
        InetAddress address = null;

        if (p >= rank) {
            if (l == 3) {
                try {
                    address = InetAddress.getByName(server);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
                switch (messageSplit[1].toLowerCase()) {
                    case "ip":
                        assert address != null;
                        bot.sendMessage(chan, "IP: " + address.getHostAddress());
                        break;
                    case "ping":
                        try {
                            assert address != null;
                            boolean canReach = address.isReachable(3000);
                            if (canReach)
                                bot.sendMessage(chan, server + " seems to be running!");
                            else
                                bot.sendMessage(chan, "Couldn't ping " + server);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        Command.throwUnknownSettingError(user, "+server", messageSplit[1]);
                        break;
                }
            } else
                Command.throwIncorrectParametersError(user, "+server IP <server>");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
