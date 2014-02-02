package net.pslice.bot.managers;

import net.pslice.bot.BotProperties;
import net.pslice.bot.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public final class PropertiesManager implements Constants {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    private HashMap<String, Boolean>

            // Map of the state of all alerts
            alerts;

    private HashMap<String, String>

            // Map of all saved links
            links;

    private Properties

            // The properties of the bot
            properties;

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

    public PropertiesManager(FileManager fileManager)
    {
        this.fileManager = fileManager;
    }





    /**
     * ===========================================
     * Method to load maps:
     *
     * @throws java.io.IOException
     * The bot will attempt to load files containing the maps
     * If the necessary files cannot be found, defaults are
     *     used and saved
     * ===========================================
     */

    public void loadFiles() throws IOException
    {
        // Check if a properties file exists. If not, create it
        if (!new File(properties_location).exists())
        {
            FileOutputStream output = new FileOutputStream(properties_location);
            BotProperties.defaultProperties().store(output, "Saved default properties");
            output.close();
        }

        // Load the bot properties, using defaults if needed
        properties = new Properties(BotProperties.defaultProperties());
        FileInputStream input = new FileInputStream(properties_location);
        properties.load(input);
        input.close();

        // Load files for alerts
        if (fileManager.fileExists(alert_location))
            alerts = fileManager.load(alert_location);
        else
        {
            alerts = BotProperties.defaultAlerts();
            fileManager.save(alerts, alert_location);
        }

        // Load files for links
        if (fileManager.fileExists(link_location))
            links = fileManager.load(link_location);
        else
        {
            links = new HashMap<>();
            fileManager.save(links, link_location);
        }
    }





    /**
     * ===========================================
     * Method to toggle the state of an alert:
     *
     * @param alert: The name of the alert
     * The state of the alert is reversed
     * Upon toggling the state, the file is saved
     * ===========================================
     */

    public void toggleAlert(String alert)
    {
        alerts.put(alert, !alerts.get(alert));
        fileManager.save(alerts, alert_location);
    }





    /**
     * ===========================================
     * Getter for the state of an alert:
     *
     * @param alert: The name of the alert
     * @return The state of the alert
     *         False if no alert was found
     * ===========================================
     */

    public boolean getAlertState(String alert)
    {
        return alerts.containsKey(alert) ? alerts.get(alert) : false;
    }





    /**
     * ===========================================
     * Getter for the existence of an alert:
     *
     * @param alert: The name of the alert:
     * @return Whether or not the alert exists
     * ===========================================
     */

    public boolean isAlert(String alert)
    {
        return alerts.containsKey(alert);
    }





    /**
     * ===========================================
     * Getter for the existence of a link:
     *
     * @param name: The name of the link
     * @return Whether or not the link exists
     * ===========================================
     */

    public boolean isLink(String name)
    {
        return links.containsKey(name);
    }





    /**
     * ===========================================
     * Setter for a link:
     *
     * @param name: The name of the link
     * @param link: The new link
     * If the link is empty, it is removed from
     *  the name
     * Upon setting the new link, the file is saved
     * ===========================================
     */

    public void setLink(String name, String link)
    {
        links.put(name, link);
        if (link.equals(""))
            links.remove(link);
        fileManager.save(links, link_location);
    }





    /**
     * ===========================================
     * Getter for a link:
     *
     * @param name: The name of the link
     * @return The link associated with the name
     *         "(None Assigned)" if no link is found
     * ===========================================
     */

    public String getLink(String name)
    {
        return links.containsKey(name) ? links.get(name) : "(None Assigned)";
    }




    /**
     * ===========================================
     * Setter for a property of the bot:
     *
     * @param property: The name of the property
     * @param value: The new value for the property
     * Upon setting the property, the file is saved
     * ===========================================
     */

    public void setProperty(String property, String value)
    {
        properties.setProperty(property, value);
        saveProperties();
    }





    /**
     * ===========================================
     * Getter for a property of the bot:
     *
     * @param property: The name of the property
     * @return The value of the property
     *         Empty string if no property is found
     * ===========================================
     */

    public String getProperty(String property)
    {
        return properties.getProperty(property, "");
    }





    /**
     * ===========================================
     * Getter for the existence of a property:
     *
     * @param property: The name of the property
     * @return Whether or not the property exists
     * ===========================================
     */

    public boolean isProperty(String property)
    {
        return properties.containsKey(property);
    }





    /*
     * ===========================================
     * Method to save the properties of the bot:
     * ===========================================
     */

    private void saveProperties()
    {
        try
        {
            FileOutputStream output = new FileOutputStream(properties_location);
            properties.store(output, "Generic property saving");
            output.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
