package net.pslice.bot.managers;

import net.pslice.bot.Constants;

import java.util.HashMap;

public final class UserManager implements Constants {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */


    private HashMap<String, Integer>

            // Map of all ranks for users
            ranks;


    private HashMap<String, String>

            // Map of all nicks for users
            nicks;

    private final FileManager

            // The file manager used by the bot
            fileManager;





    /**
     * ===========================================
     * Initializer:
     *
     * @param fileManager: The file manager used by the bot
     * ===========================================
     */

    public UserManager(FileManager fileManager)
    {
        this.fileManager = fileManager;
    }





    /*
     * ===========================================
     * Method to load maps:
     *
     * The bot will attempt to load files containing the maps
     * If the necessary files cannot be found, defaults are
     *     used and saved
     * ===========================================
     */

    public void loadFiles()
    {
        // Load files for user ranks
        if (fileManager.fileExists(user_rank_location))
            ranks = fileManager.load(user_rank_location);
        else
        {
            ranks = new HashMap<>();
            fileManager.save(ranks, user_rank_location);
        }

        // Load files for user nicks
        if (fileManager.fileExists(user_nick_location))
            nicks = fileManager.load(user_nick_location);
        else
        {
            nicks = new HashMap<>();
            fileManager.save(nicks, user_nick_location);
        }
    }





    /**
     * ===========================================
     * Setter for the rank of a user:
     *
     * @param user: The name of the user
     * @param rank: The new rank of the user
     * If the rank is equal to 0, it is removed from
     *  the map
     * Upon setting the new rank, the file is saved
     * ===========================================
     */

    public void setRank(String user, int rank)
    {
        ranks.put(user, rank);
        if (rank == 0)
            ranks.remove(user);
        fileManager.save(ranks, user_rank_location);
    }





    /**
     * ===========================================
     * Getter for the rank of a user:
     *
     * @param user: The name of the user
     * @return The rank assigned to the user
     *         0 if no rank is found
     * ===========================================
     */

    public int getRank(String user)
    {
        return ranks.containsKey(user) ? ranks.get(user) : 0;
    }





    /**
     * ===========================================
     * Setter for the nick of a user:
     *
     * @param user: The name of the user
     * @param nick: The new nick of the user
     * If the nick is empty, it is removed from
     *  the map
     * Upon setting the new nick, the file is saved
     * ===========================================
     */

    public void setNick(String user, String nick)
    {
        nicks.put(user, nick);
        if (nick.equals(user))
            nicks.remove(user);
        fileManager.save(nicks, user_nick_location);
    }





    /**
     * ===========================================
     * Getter for the nick of a user:
     *
     * @param user: The name of the user
     * @return The nick assigned to the user
     *         The original name of the user if no
     *           nick is found
     * ===========================================
     */

    public String getNick(String user)
    {
        return nicks.containsKey(user) ? nicks.get(user) : user;
    }
}
