package net.pslice.bot.managers;

import net.pslice.bot.BotProperties;
import net.pslice.bot.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

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

            // Map of all saved functions
            functions,

            // Map of all saved memories
            memories;

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

        // Load saved functions
        if (fileManager.fileExists(function_location))
            functions = fileManager.load(function_location);
        else
        {
            functions = new HashMap<>();
            fileManager.save(functions, function_location);
        }

        // Load saved memories
        if (fileManager.fileExists(memory_location))
            memories = fileManager.load(memory_location);
        else
        {
            memories = new HashMap<>();
            fileManager.save(memories, memory_location);
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
     * Getter for all alerts:
     *
     * @return A set of all alerts
     * ===========================================
     */

    public Set<String> getAlerts()
    {
        return alerts.keySet();
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





    /**
     * ===========================================
     * Setter for a memory:
     *
     * @param name: The name of the memory
     * @param memory: The new memory
     * If the memory is empty, it is removed from
     *  the name
     * Upon setting the new memory, the file is saved
     * ===========================================
     */

    public void remember(String name, String memory)
    {
        memories.put(name, memory);
        if (memory.equals(""))
            memories.remove(memory);
        fileManager.save(memories, memory_location);
    }





    /**
     * ===========================================
     * Getter for a memory:
     *
     * @param name: The name of the memory
     * @return The memory associated with the name
     *         An error if no memory is found
     * ===========================================
     */

    public String recall(String name)
    {
        return memories.containsKey(name) ? memories.get(name) : "Unfortunately, I cannot recall that.";
    }





    /**
     * ===========================================
     * Setter for a equation:
     *
     * @param function: The function the equation is saved as
     * @param equation: The new equation
     * Upon setting the new equation, the file is saved
     * ===========================================
     */

    public void saveFunction(String function, String equation)
    {
        functions.put(function, equation);
        fileManager.save(functions, function_location);
    }





    /**
     * ===========================================
     * Getter for an equation:
     *
     * @param function: The function the equation is saved as
     * @return The equation associated with the function
     *         "" if no equation is found
     * ===========================================
     */

    public String getFunction(String function)
    {
        return functions.containsKey(function) ? functions.get(function) : "";
    }
}
