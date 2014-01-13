package net.pslice.bot.managers;

import java.util.HashMap;

public final class UserManager {

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private static HashMap<String, Integer> ranks;
    private static HashMap<String, String> nicks;

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private UserManager(){}

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void loadFiles()
    {
        if (FileManager.fileExists("users.ranks"))
            ranks = FileManager.load("users.ranks");
        else
        {
            ranks = new HashMap<>();
            FileManager.save(ranks, "users.ranks");
        }

        if (FileManager.fileExists("users.nicks"))
            nicks = FileManager.load("users.nicks");
        else
        {
            nicks = new HashMap<>();
            FileManager.save(nicks, "users.nicks");
        }
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void setRank(String user, int rank)
    {
        ranks.put(user, rank);
        FileManager.save(ranks, "users.ranks");
    }

    public static int getRank(String user)
    {
        if (ranks.containsKey(user))
            return ranks.get(user);
        return 0;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void setNick(String user, String nick)
    {
        nicks.put(user, nick);
        FileManager.save(nicks, "users.nicks");
    }

    public static String getNick(String user)
    {
        if (nicks.containsKey(user))
            return nicks.get(user);
        return user;
    }
}
