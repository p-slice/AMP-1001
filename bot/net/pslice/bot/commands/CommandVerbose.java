package net.pslice.bot.commands;

import net.pslice.bot.AMP;
import net.pslice.bot.source.BotCommand;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

class CommandVerbose {

    private static final PircBotX bot = AMP.getBot();

    public static void execute(User user, String[] messageSplit, int l, int p, int rank) {

        if (p >= rank) {
            if (l == 2) {
                switch (messageSplit[1].toLowerCase()) {
                    case "on":
                        bot.setVerbose(true);
                        bot.sendNotice(user, "Verbose activated!");
                        break;
                    case "off":
                        bot.setVerbose(false);
                        bot.sendNotice(user, "Verbose deactivated!");
                        break;
                    default:
                        Command.throwImpossibleSettingError(user, "verbose", messageSplit[1], "non-Boolean");
                        break;
                }
            } else
                Command.throwIncorrectParametersError(user, "+verbose <setting>");
        } else
            Command.throwNoRankError(user, rank, p);
    }
}
