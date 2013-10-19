package commands;

import org.pircbotx.PircBotX;
import org.pircbotx.User;

import source.AMP1001;

public class CommandGet {
	
private static PircBotX bot = AMP1001.AMP;
	
	public static void getCommandList(User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;		
		
		if (p >= 0){
			if (l == 1){
				String[] commandList = source.BotCommand.getCommandList();
				for (int i = 0; i < commandList.length; i++)
					bot.sendNotice(user, commandList[i]);
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}

}
