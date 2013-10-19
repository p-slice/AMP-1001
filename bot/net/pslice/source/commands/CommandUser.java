package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.BotUser;

public class CommandUser {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void getInfo(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		String info = messageSplit[1].toLowerCase();
		
		BotUser botUser = new BotUser(messageSplit[2]);
		
		switch (info){
		case "rank":
			if (p >= 3){
				if (l == 3){					
					int q = botUser.getRank();
					System.out.println(q);
					if (q == 0)
						bot.sendMessage(chan, messageSplit[2] + "'s rank: None assigned");
					else
						bot.sendMessage(chan, messageSplit[2] + "'s rank: " + q);
				}
				else
					bot.sendNotice(user, "Incorrect parameters!");
			}
			else
				bot.sendNotice(user, "Insufficient permissions!");
			break;
		case "nick":
			if (p >= 3){
				if (l == 3){
					String userNick = botUser.getNick();
					if (userNick.equals(null))
						bot.sendMessage(chan, messageSplit[2] + "'s nick: None assigned");
					else
						bot.sendMessage(chan, messageSplit[2] + "'s nick: " + userNick);
				}
				else
					bot.sendNotice(user, "Incorrect parameters!");
			}
			else
				bot.sendNotice(user, "Insufficient permissions!");
			break;
		default:
			bot.sendNotice(user, "Unknown user setting!");
			break;
		}
	}
	public static void setInfo(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		String info = messageSplit[1].toLowerCase();
		
		BotUser botUser = new BotUser(messageSplit[2]);
		
		switch (info){
		case "rank":
			if (p >= 10){
				if (l == 4){
					botUser.setRank(messageSplit[3]);
					bot.sendMessage(chan, "Changed " + messageSplit[2] + "'s rank to " + messageSplit[3]);					
				}
				else
					bot.sendNotice(user, "Incorrect parameters!");
			}
			else
				bot.sendNotice(user, "Insufficient permissions!");
			break;
		case "nick":
			if (p >= 10){
				if (l == 4){
					botUser.setNick(messageSplit[3]);
					bot.sendMessage(chan, "Changed " + messageSplit[2] + "'s nick to " + messageSplit[3]);
				}
				else
					bot.sendNotice(user, "Incorrect parameters!");
			}
			else
				bot.sendNotice(user, "Insufficient permissions!");
			break;
		default:
			bot.sendNotice(user, "Unknown user setting!");
		}
	}
	public static void addUser(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		
		BotUser botUser = new BotUser(messageSplit[1]);
		
		if (p >= 10){
			if (l == 2){
				
				boolean isUser = botUser.addUser(messageSplit[1]);
				if (!isUser)
					bot.sendMessage(chan, "Created new user '" + messageSplit[1] +"'.");
				else
					bot.sendMessage(chan, "User already exists!");
			}
			else
				bot.sendNotice(user, "Incorrect parameters!");
		}
		else
			bot.sendNotice(user, "Insufficient permissions!");
	}
}