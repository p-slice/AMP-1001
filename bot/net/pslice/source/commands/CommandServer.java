package commands;

import java.io.IOException;
import java.net.*;

import org.pircbotx.*;

import source.AMP1001;

public class CommandServer {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public static void execute(Channel chan, User user, String[] messageSplit, int p){
		
		int l = messageSplit.length;
		String server = messageSplit[2];
		InetAddress address = null;		
		
		if (p >= 5){
			if (l == 3){
				try {
					address = InetAddress.getByName(server);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
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
					bot.sendMessage(chan, "Unknown command, '+server " + messageSplit[1] + "'");
					break;
				}
			}
			else
				bot.sendNotice(user, "Incorrect parameters! Command is '+server IP <server>'");
		}
		else
			bot.sendNotice(user, "Insufficient permissions! (Required rank: 5. Your rank: " + p +")");
	}
}
