package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class DoChat {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void doChat(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		String finalMessage;
		
		if (p >= 10){
			if (l >= 2){
				if (messageSplit[1].startsWith("#")){
					finalMessage = messageSplit[2];
					for (int i = 3; i < messageSplit.length; i++)
						finalMessage = finalMessage + " " + messageSplit[i];
					bot.sendMessage(messageSplit[1], finalMessage);
				} else {
					finalMessage = messageSplit[1];
					for (int i = 2; i < messageSplit.length; i++)
						finalMessage = finalMessage + " " + messageSplit[i];
					bot.sendMessage(chan, finalMessage);
				}
				
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}