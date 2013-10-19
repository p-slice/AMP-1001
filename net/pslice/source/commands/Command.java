package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.Users;

public class Command {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public Channel chan;
	public User user;
	public String command;
	
	public Command(Channel chan, User user, String command){
		this.chan = chan;
		this.user = user;
		this.command = command;
	}
	
	public final void execute() throws Exception {
		String[] commandSplit = command.split("[ ]");
		int l = commandSplit.length;
		String baseCommand = commandSplit[0].toLowerCase();
		
		Users userInfo = new Users(user);
		int p = userInfo.getPerms();
		
		switch(baseCommand){
		case "+quit":
			commands.CommandDisconnect.execute(user, l, p);
			break;
		case "+say":
			commands.CommandSay.execute(chan, user, command, l, p);
			break;
		case "+join":
			commands.CommandJoin.execute(user, command, l, p);
			break;
		case "+leave":
			commands.CommandLeave.execute(chan, user, command, l, p);
			break;
		case "+kick":
			commands.CommandKick.execute(chan, user, command, l, p);
			break;
		case "+info":
			commands.CommandInfo.execute(chan, user, command, l, p);
			break;
		case "+server":
			commands.CommandServer.execute(chan, user, command, l, p);
			break;
		case "+user":
			commands.CommandUser.getInfo(chan, user, command, l, p);
			break;		
		case "+setuser":
			commands.CommandUser.setInfo(chan, user, command, l, p);
			break;
		case "+adduser":
			commands.CommandUser.addUser(chan, user, command, l, p);
			break;
		case "+test":
			//source.Users.getNick();
			break;
		default:
			bot.sendNotice(user, "Unknown command.");
			break;
		}
	}

}
