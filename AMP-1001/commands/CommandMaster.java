package commands;

import org.pircbotx.*;

public class CommandMaster {
	
	public Channel chan;
	public User user;
	public String command;
	
	public CommandMaster(Channel chan, User user, String command){
		this.chan = chan;
		this.user = user;
		this.command = command;
	}
	
	public final void execute() throws Exception {
		String[] commandSplit = command.split("[ ]");
		int l = commandSplit.length;
		String baseCommand = commandSplit[0].toLowerCase();
		
		switch(baseCommand){
		case "+quit":
			commands.Disconnect.disconnect(user, l);
			break;
		case "+say":
			commands.DoChat.doChat(chan, user, command, l);
			break;
		case "+join":
			commands.JoinChan.joinChan(user, command, l);
			break;
		case "+leave":
			commands.LeaveChan.leaveChan(chan, user, command, l);
			break;
		case "+kick":
			commands.Kick.kick(chan, user, command, l);
			break;
		default: break;
		}
	}

}
