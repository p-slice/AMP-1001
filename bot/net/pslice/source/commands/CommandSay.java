package commands;

import org.pircbotx.*;

import source.AMP1001;

public class CommandSay {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		String finalMessage;
		
		if (p >= 10){
			if (l >= 3 && messageSplit[1].startsWith("#")){
				finalMessage = messageSplit[2];
				for (int i = 3; i < messageSplit.length; i++)
					finalMessage = finalMessage + " " + messageSplit[i];
				bot.sendMessage(messageSplit[1], finalMessage);
			}
			else if (l >=2){
				finalMessage = messageSplit[1];
				for (int i = 2; i < messageSplit.length; i++)
					finalMessage = finalMessage + " " + messageSplit[i];
				bot.sendMessage(chan, finalMessage);
			}
			else
				bot.sendNotice(user, "Incorrect parameters! Command is '+say (channel) <message>'");
		}
		else
			bot.sendNotice(user, "Insufficient permissions! (Required rank: 10. Your rank: " + p +")");
	}

}