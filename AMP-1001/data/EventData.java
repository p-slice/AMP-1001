package data;

import org.pircbotx.*;
import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.*;

import source.AMP1001;

@SuppressWarnings("rawtypes")
public class EventData extends ListenerAdapter {
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public void onJoin(JoinEvent event){
		Channel chan = event.getChannel();
		User user = event.getUser();
		
		if (chan.getName().equals("#p_slice") && !user.getNick().equals(bot.getNick()))
			bot.sendMessage(chan, "Welcome, " + user.getNick());
	}
}