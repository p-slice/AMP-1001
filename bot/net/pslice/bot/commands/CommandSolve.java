package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.MathFunctions;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.Serializable;
import java.util.List;

public final class CommandSolve extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandSolve()
    {
        super("solve", 0, "<equation>", "Solve a simple equation", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command will solve a basic equation and return
     *     the answer
     * For more info see the MathFunctions class
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        // Make sure there is something to solve
        if (args.length > 0)
        {
            String fullEquation = "";
            for (String arg : args)
                fullEquation += arg;

            fullEquation = MathFunctions.replaceValues(fullEquation, "pi", String.valueOf(Math.PI));

            List<String> result = MathFunctions.splitEquation(fullEquation);

            String finalResult = MathFunctions.simplifyEquation(bot, sender, result);

            if (finalResult == null)
                return;

            bot.sendMessage(channel, "Result: " + finalResult.replaceAll("\\.0\\b", ""));
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
