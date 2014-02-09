package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import net.pslice.bot.managers.CommandManager;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public final class CommandRestart extends Command implements Serializable {

    /*
     * ===========================================
     * Initializer:
     *
     * The master Command class is initialized with defaults
     *     specific to the command
     * ===========================================
     */

    public CommandRestart()
    {
        super("restart", 10, "", "Restart the bot", true);
    }

    /**
     * ===========================================
     * Command execution method:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param args: The arguments sent with the command
     * This command completely restarts the program, thanks
     *     to a bit of magic I found while browsing the internet
     * Note: This technically only works when running the bot
     *     from a .jar file
     * ===========================================
     */

    public void execute(AmpBot bot, Channel channel, User sender, String... args)
    {
        if (args.length == 0)
        {
            try
            {
                final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                final File currentJar = new File(AmpBot.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if(!currentJar.getName().endsWith(".jar"))
                    return;

                final ArrayList<String> command = new ArrayList<>();
                command.add(javaBin);
                command.add("-jar");
                command.add(currentJar.getPath());

                final ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();
                System.exit(0);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else
            CommandManager.throwIncorrectParametersError(bot, sender, this);
    }
}
