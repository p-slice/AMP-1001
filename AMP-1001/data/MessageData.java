package data;

import org.pircbotx.*;
import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.*;

import commands.CommandMaster;

@SuppressWarnings("rawtypes")
public class MessageData extends ListenerAdapter {
	
	public void onMessage(MessageEvent event) throws Exception{
		Channel chan = event.getChannel();
		User user = event.getUser();
		
		String message = event.getMessage();
		
		if (message.startsWith("+")){
			CommandMaster command = new CommandMaster(chan, user, message);
			command.execute();
		}
		boolean containsLanguage = data.AdminData.checkLanguage(message);
		if (containsLanguage == true){
			commands.Kick.languageKick(chan, user);
		}
	}
}
