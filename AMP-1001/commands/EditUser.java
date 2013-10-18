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
import source.UserInfo;

public class EditUser {
	
	static Charset charset = Charset.defaultCharset();
	
	private static PircBotX bot = AMP1001.AMP1001;
	
	public static void setRank(Channel chan, User user, String message, int l){
		
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 10){
			if (l == 3){
				String fileName = "user." + messageSplit[1] + ".txt";
				
				Path filePath = new File(fileName).toPath();
				List<String> stringList = null;
				try {
					stringList = Files.readAllLines(filePath, charset);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String[] stringArray = stringList.toArray(new String[]{});
				String editLine = stringArray[2];
				
				editLine = editLine.replace(stringArray[2], messageSplit[2]);
				String content = null;
				try {
					content = new String(Files.readAllBytes(filePath), charset);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				content = content.replaceAll(stringArray[2], editLine);
				try {
					Files.write(filePath, content.getBytes(charset));
					bot.sendMessage(chan, "Changed " + messageSplit[1] + "'s rank to " + messageSplit[2]);
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
	public static void addUser(Channel chan, User user, String message, int l){
		UserInfo userInfo = new UserInfo(user);
		int p = userInfo.getPerms();
		
		String[] messageSplit = message.split("[ ]");
		
		if (p >= 10){
			if (l == 2){
				String fileName = "user." + messageSplit[1] + ".txt";				
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
						
						bot.sendMessage(chan, "Created new user " + messageSplit[1]);
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