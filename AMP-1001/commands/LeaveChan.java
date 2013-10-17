package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class LeaveChan {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void leaveChan(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 7){
			if (l == 1)
				bot.partChannel(chan);
			else if (l == 2) 
				bot.partChannel(bot.getChannel(messageSplit[1]));
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}