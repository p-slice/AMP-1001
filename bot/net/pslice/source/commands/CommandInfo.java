package commands;

import org.pircbotx.*;

import source.AMP1001;

public class CommandInfo {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		
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
