package data;

import org.pircbotx.*;
import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.*;

import commands.Command;

@SuppressWarnings("rawtypes")
public class MessageData extends ListenerAdapter {
	
	public void onMessage(MessageEvent event) throws Exception{
		Channel chan = event.getChannel();
		User user = event.getUser();
		
		String message = event.getMessage();
		
		if (message.startsWith("+")){
			Command command = new Command(chan, user, message);
			command.execute();
		}
		boolean containsLanguage = data.AdminData.checkLanguage(message);
		if (containsLanguage == true && chan.getName().equals("#p_slice")){
			commands.CommandKick.languageKick(chan, user);
		}
	}
}
