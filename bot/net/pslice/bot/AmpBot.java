package net.pslice.bot;

import net.pslice.bot.commands.CommandLink;
import net.pslice.bot.listeners.MessageData;
import net.pslice.bot.listeners.PrivateMessageData;
import net.pslice.bot.managers.CommandManager;
import net.pslice.bot.managers.UserManager;
import org.pircbotx.PircBotX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public final class AmpBot extends PircBotX {

    public static void main(String[] args)
    {
        AmpBot.run();
    }

    /*
    * ===========================================
    * Variables, Objects, Lists, Sets and Maps:
    * ===========================================
     */

    // The main PircBotX used
    private static PircBotX bot;

    // The main properties used by the bot
    private static Properties properties;

    // Booleans concerning the way input is registered
    private static boolean override, inputType;

    /*
    * ===========================================
    * Method to run the bot:
    * ===========================================
     */

    public static void run()
    {
        try
        {
            loadAllFiles();

            bot = new PircBotX();

            bot.setName(properties.getProperty("nick"));
            bot.setLogin(properties.getProperty("login"));
            bot.setVersion(properties.getProperty("realname"));
            bot.setVerbose(properties.getProperty("verbose").equals("true"));
            bot.setAutoNickChange(properties.getProperty("changenick").equals("true"));
            bot.setCapEnabled(properties.getProperty("identify").equals("true"));

            bot.getListenerManager().addListener(new MessageData());
            bot.getListenerManager().addListener(new PrivateMessageData());

            bot.connect(properties.getProperty("server"));
            bot.joinChannel(properties.getProperty("channel"));
            if (bot.isCapEnabled())
                bot.identify(properties.getProperty("nickservPASS"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static boolean isProperty(String setting)
    {
        return properties.containsKey(setting);
    }

    public static void setProperty(String setting, String value)
    {
        properties.setProperty(setting, value);
        saveProperties();
    }

    public static String getProperty(String setting)
    {
        if (properties.containsKey(setting))
            return properties.getProperty(setting);
        return null;
    }

    private static void saveProperties()
    {
        try
        {
            FileOutputStream output = new FileOutputStream("Files/BotInfo.properties");
            properties.store(output, "");
            output.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static PircBotX getBot()
    {
        return bot;
    }

    public static void toggleOverride()
    {
        override = !override;
    }

    public static boolean isOverride()
    {
        return override;
    }

    public static void toggleInput()
    {
        inputType = !inputType;
    }

    public static boolean getInputType()
    {
        return inputType;
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static void loadAllFiles()
    {
        try
        {
            override = false;
            inputType = false;

            if (!new File("Files/BotInfo.properties").exists())
            {
                FileOutputStream output = new FileOutputStream("Files/BotInfo.properties");
                BotProperties.defaultProperties().store(output, "");
            }

            properties = new Properties(BotProperties.defaultProperties());
            FileInputStream input = new FileInputStream("Files/BotInfo.properties");
            properties.load(input);
            input.close();

            CommandManager.loadFiles();
            UserManager.loadFiles();
            CommandLink.loadFiles();

            if (UserManager.getRank(properties.getProperty("master")) < 10)
                UserManager.setRank(properties.getProperty("master"), 10);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
