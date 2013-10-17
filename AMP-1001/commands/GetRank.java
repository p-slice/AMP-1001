package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class GetRank {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void getRank(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 3){
			if (l == 2){
				UserInfo demoInfo = new UserInfo(bot.getUser(messageSplit[1]));
				int q = demoInfo.getPerms();
				if (q == 0)
					bot.sendMessage(chan, messageSplit[1] + "'s rank: None assigned");
				else
					bot.sendMessage(chan, messageSplit[1] + "'s rank: " + q);
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}