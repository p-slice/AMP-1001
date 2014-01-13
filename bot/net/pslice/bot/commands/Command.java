package net.pslice.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public interface Command {

    public void execute(PircBotX bot, Channel channel, User sender, String command, String... args);

}
