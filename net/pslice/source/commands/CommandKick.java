package commands;

import org.pircbotx.*;

import source.AMP1001;

public class CommandKick {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(Channel chan, User user, String message, int l, int p){
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 10){
			if (l == 2)
				bot.kick(chan, bot.getUser(messageSplit[1]), "Kicked from channel.");
			else if (l > 2){
				String reason = messageSplit[2];
				for (int i = 3; i < messageSplit.length; i++)
					reason = reason + " " + messageSplit[i];
				bot.kick(chan, bot.getUser(messageSplit[1]), reason);
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}
	
	public static void languageKick(Channel chan, User user){
		bot.kick(chan, user, "That sort of language is not allowed in here.");
	}
}
