package net.pslice.bot;

import net.pslice.bot.listeners.ChannelData;
import net.pslice.bot.listeners.MessageData;
import net.pslice.bot.listeners.ServerData;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.FileManager;
import net.pslice.bot.managers.PropertiesManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.PircBotX;

public final class AmpBot extends PircBotX {

    /*
     * ===========================================
     * Main static method:
     * ===========================================
     */

    public static void main(String[] args)
    {
        AmpBot ampBot = new AmpBot();
        ampBot.run();
    }





    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    public static final String

            version = "3.2.2";

    private boolean

            // Whether or not the bot is in override mode
            override,

            // Whether or not the bot uses pre-defined ranks
            useChannelRanks;

    private final FileManager

            // Manager used for saving/loading files
            fileManager = new FileManager("Files");

    private final PropertiesManager

            // Manager used for all bot properties
            propertiesManager = new PropertiesManager(fileManager);

    private final CommandManager

            // Manager used for all bot commands
            commandManager = new CommandManager(fileManager);

    private final UserManager

            // Manager used for all bot users
            userManager = new UserManager(fileManager);





    /**
     * ===========================================
     * Getters for various bot managers:
     *
     * @return fileManager
     *         propertiesManager
     *         commandManager
     *         userManager
     * ===========================================
     */

    public FileManager getFileManager()
    {
        return fileManager;
    }

    public PropertiesManager getPropertiesManager()
    {
        return propertiesManager;
    }

    public CommandManager getCommandManager()
    {
        return commandManager;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }





    /*
     * ===========================================
     * Method to run the bot:
     * ===========================================
     */

    public void run()
    {
        try
        {
            // Make sure all the files needed to initialize the bot are ready
            loadAllFiles();

            // Set the nick of the bot
            this.setName(propertiesManager.getProperty("nick"));
            // Set the login of the bot
            this.setLogin(propertiesManager.getProperty("login"));
            // Set the real name/version of the bot
            this.setVersion(propertiesManager.getProperty("realname"));
            // Set whether or not the bot prints logs to console
            this.setVerbose(propertiesManager.getProperty("verbose").equals("true"));
            // Set whether or not the bot will try rename upon joining if its name it taken
            this.setAutoNickChange(propertiesManager.getProperty("changenick").equals("true"));
            // Set whether or not the bot will try identify with NickServ upon joining
            this.setCapEnabled(propertiesManager.getProperty("identify").equals("true"));

            // Add Listeners to the bot
            this.getListenerManager().addListener(new MessageData());
            this.getListenerManager().addListener(new ChannelData());
            this.getListenerManager().addListener(new ServerData());

            // Connect to the server
            this.connect(propertiesManager.getProperty("server"));

            // Join any channels
            for (String channel : propertiesManager.getProperty("channels").split(" "))
                this.joinChannel(channel);

            // Identify if instructed to
            if (this.isCapEnabled())
                this.identify(propertiesManager.getProperty("nickservPASS"));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }





    /*
     * ===========================================
     * Method to toggle the state of override:
     * ===========================================
     */

    public void toggleOverride()
    {
        override = !override;
    }





    /**
     * ===========================================
     * Getter for the state of override:
     *
     * @return The state of override
     * ===========================================
     */

    public boolean isOverride()
    {
        return override;
    }





    /*
     * ===========================================
     * Method to toggle the use of channel ranks:
     * ===========================================
     */

    public void toggleRankInput()
    {
        useChannelRanks = !useChannelRanks;
    }





    /**
     * ===========================================
     * Getter for the state of using channel ranks:
     *
     * @return The state of useChannelRanks
     * ===========================================
     */

    public boolean usesChannelRanks()
    {
        return useChannelRanks;
    }





    /*
     * ===========================================
     * Method to ensure all files are loaded:
     * ===========================================
     */

    public void loadAllFiles()
    {
        try
        {
            // Set boolean states to default
            override = false;
            useChannelRanks = false;

            // Load files in individual managers
            propertiesManager.loadFiles();
            commandManager.loadFiles();
            userManager.loadFiles();

            // Ensure the bot Master has rank 10
            if (userManager.getRank(propertiesManager.getProperty("master")) < 10)
                userManager.setRank(propertiesManager.getProperty("master"), 10);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
