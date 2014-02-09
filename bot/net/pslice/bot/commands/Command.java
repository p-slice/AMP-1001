package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public abstract class Command implements Serializable {

    /*
     * ===========================================
     * Command defaults:
     * ===========================================
     */

    public final String

            // The name of the command
            name;

    private final int

            // The default rank for the command
            defaultRank;

    private final String

            // The default parameters for the command
            defaultParameters,
            // The default description of the command
            defaultDescription;

    private final boolean

            // The default enabled state of the command
            defaultEnabled;





    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    protected int

            // The current rank of the command
            rank;

    protected String

            // The current parameters for the command
            parameters,
            // The current description of the command
            description;

    protected boolean

            // The current enabled state of the command
            enabled;





    /**
     * ===========================================
     * Initializer:
     *
     * @param name: The name of the command
     * @param defaultRank: The default rank of the command
     * @param defaultParameters: The default parameters for the command
     * @param defaultDescription: The default description of the command
     * @param defaultEnabled: The default enabled state of the command
     * ===========================================
     */

    public Command(String name, int defaultRank, String defaultParameters, String defaultDescription, boolean defaultEnabled)
    {
        this.name = name;

        this.defaultRank = defaultRank;
        rank = defaultRank;
        this.defaultParameters = defaultParameters;
        parameters = defaultParameters;
        this.defaultDescription = defaultDescription;
        description = defaultDescription;
        this.defaultEnabled = defaultEnabled;
        enabled = defaultEnabled;
    }





    /**
     * ===========================================
     * Abstract method to execute the command:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param user: The user the command was sent to
     * @param args: The arguments sent with the command
     * ===========================================
     */

    public abstract void execute(AmpBot bot, Channel channel, User user, String... args);





    /*
     * ===========================================
     * Method to reset the command settings to their defaults:
     * ===========================================
     */

    public void reset()
    {
        rank = defaultRank;
        parameters = defaultParameters;
        description = defaultDescription;
        enabled = defaultEnabled;
    }





    /**
     * ===========================================
     * Setter for the rank of the command:
     *
     * @param rank: The new rank of the command
     * ===========================================
     */

    public void setRank(int rank)
    {
        this.rank = rank;
    }





    /**
     * ===========================================
     * Getter for the rank of the command:
     *
     * @return The current rank of the command
     * ===========================================
     */

    public int getRank()
    {
        return rank;
    }





    /**
     * ===========================================
     * Setter for the parameters for the command:
     *
     * @param parameters: The new parameters for the command
     * ===========================================
     */

    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }





    /**
     * ===========================================
     * Getter for the parameters for the command:
     *
     * @return The current parameters for the command
     * ===========================================
     */

    public String getParameters()
    {
        return parameters;
    }





    /**
     * ===========================================
     * Setter for the description of the command:
     *
     * @param description: The new description of the command
     * ===========================================
     */

    public void setDescription(String description)
    {
        this.description = description;
    }





    /**
     * ===========================================
     *  Getter for the description of the command:
     *
     *  @return The current description of the command
     * ===========================================
     */

    public String getDescription()
    {
        return description;
    }





    /**
     * ===========================================
     * Setter for the enabled state of the command:
     *
     * @param enabled: The new enabled state of the command
     * ===========================================
     */

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }





    /**
     * ===========================================
     * Getter for the enabled state of the command:
     *
     * @return The current enabled state of the command
     * ===========================================
     */

    public boolean isEnabled()
    {
        return enabled;
    }
}
