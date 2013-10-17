package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class Disconnect {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void disconnect(User user, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		if (p >= 10){
			if (l == 1)
				bot.quitServer("Disconnecting");
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}
