package net.pslice.bot.commands;

import org.pircbotx.User;

class CommandVerbose extends Command {

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
                        throwImpossibleSettingError(user, "verbose", messageSplit[1], "non-Boolean");
                        break;
                }
            } else
                throwIncorrectParametersError(user, "+verbose <setting>");
        } else
            throwNoRankError(user, rank, p);
    }
}
