package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.Probability;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

public final class CommandTwerk implements Command {

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: The arguments sent with the command
     * This command is similar to the 'Roulette' commands
     *     in other bots, however this style relates it
     *     to twerking
     * The bot must have Op in the channel to do the command
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String command, String... args)
    {
        // Command requires no arguments
        if (args.length == 0)
        {
            // Check if the bot has Op in the channel
            if(channel.getOps().contains(bot.getUserBot()))
            {
                bot.sendMessage(channel, String.format("Looks like %s is willing to twerk for us now...", sender.getNick()));
                bot.sendMessage(channel, "But do they have what it takes to be a professional?");

                // Create a new probability, 2 for 'living' and 1 for 'dying'
                Probability<Boolean> probability = new Probability<>();
                probability.add(true, 2);
                probability.add(false, 1);

                // Get the result of the probability, and take action from the result
                if(probability.getResult())
                    bot.sendMessage(channel, String.format("Wow, %s sure is skilled! Soon they'll be twerking for all their friends!", sender.getNick()));
                else
                {
                    bot.sendMessage(channel, String.format("Oh dear, %s, you'll have to do better than that.", sender.getNick()));
                    bot.kick(channel, sender, "You've twerked your way right out of the channel!");
                }
            }

            // Become lazy if no Op powers
            else
                channel.sendMessage("Sorry, I don't feel fit for judging twerk sessions right now.");
        }

        // Throw an error if the parameters are incorrect
        else
            CommandManager.throwIncorrectParametersError(bot, sender, command);
    }
}
