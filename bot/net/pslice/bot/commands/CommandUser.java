package net.pslice.bot.commands;

import net.pslice.bot.source.BotUser;
import org.pircbotx.Channel;
import org.pircbotx.User;

class CommandUser extends Command {

    private static boolean userType = false;

    public static boolean isOpBased() {
        return userType;
    }

    public static void toggleUserType(Channel chan, User user, int l, int p, int rank) {
        if (p >= rank) {
            if (l == 1) {
                userType = !userType;
                if (!userType)
                    bot.sendMessage(chan, "User acceptance toggled to custom ranks.");
                else
                    bot.sendMessage(chan, "User acceptance toggled to channel permissions.");
            } else
                throwIncorrectParametersError(user, "+toggleuser");
        } else
            throwNoRankError(user, rank, p);
    }

    public static void getInfo(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        String info = messageSplit[1].toLowerCase();

        if (messageSplit.length < 3) {
            throwIncorrectParametersError(user, "+user <info> <user>");
            return;
        }
        BotUser botUser = new BotUser(messageSplit[2]);

        if (p >= rank) {
            if (l == 3) {
                switch (info) {
                    case "rank":
                        int q = botUser.getRank();
                        System.out.println(q);
                        if (q == 0)
                            bot.sendMessage(chan, messageSplit[2] + "'s rank: None assigned");
                        else
                            bot.sendMessage(chan, messageSplit[2] + "'s rank: " + q);
                        break;
                    case "nick":
                        String userNick = botUser.getNick();
                        if (userNick.equals(""))
                            bot.sendMessage(chan, messageSplit[2] + "'s nick: None assigned");
                        else
                            bot.sendMessage(chan, messageSplit[2] + "'s nick: " + userNick);
                        break;
                    default:
                        throwUnknownSettingError(user, messageSplit[0], messageSplit[1]);
                        break;
                }
            } else
                throwIncorrectParametersError(user, "+user <info> <user>");
        } else
            throwNoRankError(user, rank, p);
    }

    public static void setInfo(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {
        String info = messageSplit[1].toLowerCase();

        BotUser botUser = new BotUser(messageSplit[2]);

        if (p >= rank) {
            if (l == 4) {
                switch (info) {
                    case "rank":
                        boolean a = false;
                        try {
                            Integer.parseInt(messageSplit[3]);
                            a = true;
                        } catch (Exception e) {
                            throwImpossibleSettingError(user, "user rank", messageSplit[3], "String");
                        }
                        if (a) {
                            int g = Integer.parseInt(messageSplit[3]);
                            if (messageSplit[2].equalsIgnoreCase("p_slice") && g < 10) {
                                bot.sendNotice(user, "Hey, you can't do that!");
                                break;
                            }
                            botUser.setRank(messageSplit[3]);
                            bot.sendMessage(chan, "Changed " + messageSplit[2] + "'s rank to " + messageSplit[3]);
                        }
                        break;
                    case "nick":
                        botUser.setNick(messageSplit[3]);
                        bot.sendMessage(chan, "Changed " + messageSplit[2] + "'s nick to " + messageSplit[3]);
                        break;
                    default:
                        throwUnknownSettingError(user, messageSplit[0], messageSplit[1]);
                        break;
                }
            } else
                throwIncorrectParametersError(user, "+setuser <info> <user> <new info>");
        } else
            throwNoRankError(user, rank, p);
    }

    public static void addUser(Channel chan, User user, String[] messageSplit, int l, int p, int rank) {

        BotUser botUser = new BotUser(messageSplit[1]);

        if (p >= rank) {
            if (l >= 2 && l <= 4) {
                boolean isUser = botUser.addUser(messageSplit[1]);
                if (!isUser)
                    bot.sendMessage(chan, "Created new user '" + messageSplit[1] + "'.");
                else {
                    bot.sendMessage(chan, "User already exists!");
                    return;
                }
                BotUser newUser = new BotUser(messageSplit[1]);
                if (l == 3) {
                    boolean a = false;
                    try {
                        Integer.parseInt(messageSplit[3]);
                        a = true;
                    } catch (Exception e) {
                        throwImpossibleSettingError(user, "user rank", messageSplit[3], "String");
                    }
                    if (a)
                        newUser.setRank(messageSplit[2]);
                }
                if (l == 4) {
                    boolean a = false;
                    try {
                        Integer.parseInt(messageSplit[3]);
                        a = true;
                    } catch (Exception e) {
                        throwImpossibleSettingError(user, "user rank", messageSplit[3], "String");
                    }
                    if (a)
                        newUser.setRank(messageSplit[2]);
                    newUser.setNick(messageSplit[3]);
                }
            } else
                throwIncorrectParametersError(user, "+adduser <name> (rank) (nick)");
        } else
            throwNoRankError(user, rank, p);
    }
}
