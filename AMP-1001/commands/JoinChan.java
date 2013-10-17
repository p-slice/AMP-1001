package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class JoinChan {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void joinChan(User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");		
		
		if (p >= 7){
			if (l == 2)
				bot.joinChannel(messageSplit[1]);
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}
