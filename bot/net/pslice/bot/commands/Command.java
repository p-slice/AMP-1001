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
     *     individually. The current notes in the separate classes
     *     are scarce, but they should soon be updated with their
     *     own info
     * ===========================================
     */

    void execute(AmpBot bot, Channel channel, User sender, String command, String... args);

}
