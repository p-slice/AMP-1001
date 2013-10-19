package commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.pircbotx.*;

import source.AMP1001;
import source.Users;

public class CommandUser {
	
	private static PircBotX bot = AMP1001.AMP;
	
	static Charset charset = Charset.defaultCharset();
	
	public static void getInfo(Channel chan, User user, String message, int l, int p){
		
		String[] messageSplit = message.split("[ ]");
		String info = messageSplit[1].toLowerCase();
		
		switch (info){
		case "rank":
			if (p >= 3){
				if (l == 3){
					Users userInfo = new Users(bot.getUser(messageSplit[2]));
					int q = userInfo.getPerms();
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
					Users userInfo = new Users(bot.getUser(messageSplit[2]));
					String userNick = userInfo.getNick();
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
			bot.sendNotice(user, "Couldn't understand command!");
			break;
		}
	}
	public static void setInfo(Channel chan, User user, String message, int l, int p){
		String[] messageSplit = message.split("[ ]");
		String info = messageSplit[1].toLowerCase();
		
		switch (info){
		case "rank":
			if (p >= 10){
				if (l == 3){
					String fileName = "Files/user." + messageSplit[2] + ".txt";
					
					Path filePath = new File(fileName).toPath();
					List<String> stringList = null;
					try {
						stringList = Files.readAllLines(filePath, charset);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String[] stringArray = stringList.toArray(new String[]{});
					String editLine = stringArray[2];
					
					editLine = editLine.replace(stringArray[2], messageSplit[3]);
					String content = null;
					try {
						content = new String(Files.readAllBytes(filePath), charset);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					content = content.replaceAll(stringArray[2], editLine);
					try {
						Files.write(filePath, content.getBytes(charset));
						bot.sendMessage(chan, "Changed " + messageSplit[1] + "'s rank to " + messageSplit[3]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
					bot.sendNotice(user, "Incorrect parameters!");
			}
			else
				bot.sendNotice(user, "Insufficient permissions!");
		}
	}
	public static void addUser(Channel chan, User user, String message, int l, int p){
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 10){
			if (l == 2){
				String fileName = "Files/user." + messageSplit[1] + ".txt";				
				File f = new File(fileName);
				
				if(!f.exists()){
					try {
						FileWriter fileWriter = new FileWriter(fileName, true);
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		            
						
						bufferedWriter.append(messageSplit[1]);
						bufferedWriter.newLine();
						bufferedWriter.append("//");
						bufferedWriter.newLine();
						bufferedWriter.append("1");
						bufferedWriter.close();
						
						bot.sendMessage(chan, "Created new user '" + messageSplit[1] +"'.");
					}
					catch(IOException ex) {
						System.out.println("Error writing to file '" + fileName + "'");
					}
				}
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