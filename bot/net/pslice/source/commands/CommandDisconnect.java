package commands;

import org.pircbotx.*;

import source.AMP1001;

public class CommandDisconnect {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(User user, int l, int p){
				
		if (p >= 10){
			if (l == 1)
				bot.quitServer("Disconnecting");
			else
				bot.sendNotice(user, "Incorrect parameters! Command is '+quit'");
		}
		else
			bot.sendNotice(user, "Insufficient permissions! (Required rank: 10. Your rank: " + p +")");
	}

}
