package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.MathFunctions;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;

public final class CommandFunction extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandFunction()
    {
        super("function", 5, "f(x) = <equation>", "Solve a function for x", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command saves the equation of a functions,
     *     or returns the current equation saved in
     *     a function
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Two arguments - get the equation saved in a function
        if (args.length == 2)
        {
            // Make sure a function is specified
            if (args[0].matches("[a-z]\\(x\\)")
                    && args[1].equals("="))
            {
                String equation = bot.getPropertiesManager().getFunction(args[0]);

                // Check if there is an equation saved with the function
                if (equation.equals(""))
                    bot.sendMessage(channel, "No function is currently saved there.");
                else
                    bot.sendMessage(channel, args[0] + " = " + equation);
            }

            // Throw an error if there isn't
            else
                CommandManager.throwIncorrectParametersError(bot, sender, this);
        }

        // More than two arguments - save a new equation
        else if (args.length > 2)
        {
            String fullFunction = "";
            for (String arg : args)
                fullFunction += arg;

            // An '=' sign must be present!
            if (!fullFunction.contains("="))
            {
                CommandManager.throwGenericError(bot, sender, "No '=' was found in your function!");
                return;
            }

            String[] functionSplit = fullFunction.split("=");

            // Make sure its a function being saved to
            if (functionSplit[0].matches("[a-z]\\(x\\)"))
            {
                functionSplit[1] = MathFunctions.replaceValues(functionSplit[1], "pi", String.valueOf(Math.PI));

                // If the equation argument is another function, save the equation of that function
                if (functionSplit[1].matches("[a-z]\\(.*x.*\\)"))
                {
                    bot.getPropertiesManager().saveFunction(functionSplit[0], bot.getPropertiesManager().getFunction(functionSplit[1].charAt(0) + "(x)").replaceAll("x", functionSplit[1].substring(1)));
                    bot.sendMessage(channel, String.format("The equation of %s is now the equation of %s", functionSplit[0], functionSplit[1]));
                }

                // Otherwise save it as a new equation
                else
                {
                    bot.getPropertiesManager().saveFunction(functionSplit[0], functionSplit[1]);
                    bot.sendMessage(channel, "Function saved.");
                }
            }

            // Throw an error if it isn't
            else
                CommandManager.throwGenericError(bot, sender, "You must begin your function in the form 'f(x)'!");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
