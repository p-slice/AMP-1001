package commands;

import org.pircbotx.*;

import source.AMP1001;
import source.BotUser;

public class Command {
	
	private static PircBotX bot = AMP1001.AMP;
	
	public Channel chan;
	public User user;
	public String[] messageSplit;
	
	public Command(Channel chan, User user, String message){
		this.chan = chan;
		this.user = user;
		this.messageSplit = message.split("[ ]");
	}
	
	public final void execute() throws Exception {
		
		int l = messageSplit.length;
		String baseCommand = messageSplit[0].toLowerCase();
		
		BotUser botUser = new BotUser(user.getNick().toString());
		int p = botUser.getRank();
		
		switch(baseCommand){
		case "+quit":
			commands.CommandDisconnect.execute(user, l, p);
			break;
		case "+say":
			commands.CommandSay.execute(chan, user, messageSplit, p);
			break;
		case "+join":
			commands.CommandJoin.execute(user, messageSplit, p);
			break;
		case "+leave":
			commands.CommandLeave.execute(chan, user, messageSplit, p);
			break;
		case "+kick":
			commands.CommandKick.execute(chan, user, messageSplit, p);
			break;
		case "+info":
			commands.CommandInfo.execute(chan, user, messageSplit, p);
			break;
		case "+server":
			commands.CommandServer.execute(chan, user, messageSplit, p);
			break;
		case "+user":
			commands.CommandUser.getInfo(chan, user, messageSplit, p);
			break;		
		case "+setuser":
			commands.CommandUser.setInfo(chan, user, messageSplit, p);
			break;
		case "+adduser":
			commands.CommandUser.addUser(chan, user, messageSplit, p);
			break;
		case "+commands":
			commands.CommandGet.getCommandList(user, messageSplit, p);
			break;
		default:
			bot.sendNotice(user, "Unknown command.");
			break;
		}
	}

}
