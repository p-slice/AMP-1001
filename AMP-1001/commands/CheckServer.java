package commands;

import java.io.IOException;
import java.net.*;

import org.pircbotx.*;

import source.AMP1001;
import source.UserInfo;

public class CheckServer {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void ping(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		String server = messageSplit[2];
		InetAddress address = null;
		try {
			address = InetAddress.getByName(server);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		if (p >= 5){
			if (l == 3){				
				switch (messageSplit[1].toLowerCase()){
				case "ip":
					bot.sendMessage(chan, "IP: " + address.getHostAddress());
					break;
				case "ping":
					try {
						boolean canReach = address.isReachable(3000);
						if (canReach == true)
							bot.sendMessage(chan, server + " seems to be running!");
						else
							bot.sendMessage(chan, "Couldn't ping " + server);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;						
				default:
					bot.sendMessage(chan, "Couldn't resolve command");
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
