package net.pslice.bot.commands;

import net.pslice.bot.AmpBot;
import org.pircbotx.Channel;
import org.pircbotx.User;

public interface Command {

    /**
     * ===========================================
     * Execution method for any command:
     *
     * @param bot: The bot the command was sent to
     * @param channel: The channel the command was sent in
     * @param sender: The user the command was sent by
     * @param command: The name of the command
     * @param args: Any arguments that were sent with the command
     * The execution process is controlled by each command class
     *     individually. View those classes for info about
     *     each command
     * ===========================================
     */

    void execute(AmpBot bot, Channel channel, User sender, String command, String... args);

}
