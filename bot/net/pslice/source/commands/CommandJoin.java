package commands;

import org.pircbotx.*;

import source.AMP1001;

public class CommandJoin {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(User user, String message, int l, int p){
		
		String[] messageSplit = message.split("[ ]");		
		
		if (p >= 7){
			if (l == 2)
				if (messageSplit[1].startsWith("#"))
					bot.joinChannel(messageSplit[1]);
				else
					bot.sendNotice(user, "That's not a channel!");
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}