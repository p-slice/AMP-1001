package data;

import org.pircbotx.*;
import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.*;

import source.AMP1001;
import source.Users;

@SuppressWarnings("rawtypes")
public class EventData extends ListenerAdapter {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public void onJoin(JoinEvent event){
		Channel chan = event.getChannel();
		User user = event.getUser();
		
		if (chan.getName().equals("#p_slice") && !user.getNick().equals(bot.getNick()))
			bot.sendMessage(chan, "Welcome, " + user.getNick());
		
		Users userInfo = new Users(user);
		int p = userInfo.getPerms();
		
		if (p >= 5 && chan.getName().equals("#p_slice")){
			bot.voice(chan, user);
		}
	}
}