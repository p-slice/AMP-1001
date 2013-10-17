package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class Info {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void info(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 3){
			if (l == 2){
				switch (messageSplit[1].toLowerCase()){
				case "nick":
					bot.sendMessage(chan, "Nick: " + bot.getNick());
					break;
				case "login":
					bot.sendMessage(chan, "Login: " + bot.getLogin());
					break;
				case "password":
					bot.sendMessage(chan, "Password: Ahahahaha nope.");
					break;
				case "network":
					bot.sendMessage(chan, "Network: " + bot.getServer());
					break;
				default:
					bot.sendNotice(user, "Unrecognized info commands");
					break;
				}
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}
