package source;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import org.pircbotx.*;

public class Users {
	
	static Charset charset = Charset.defaultCharset();
	
	public String userNick;
	public String userLogin;
	public String userHostMask;
	
	public Users(User user){
		this.userNick = user.getNick();
		this.userLogin = user.getLogin();
		this.userHostMask = user.getHostmask();
	}
	
	public String getNick(){
		File f = new File("Files/user." + userNick + ".txt");
        if(!f.exists())
                return null;
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(f.toPath(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringArray = stringList.toArray(new String[]{});
		
		userNick = stringArray[0];
		return userNick;
	}
	
	public int getPerms(){
		File f = new File("Files/user." + userNick + ".txt");
        if(!f.exists())
                return 0;
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(f.toPath(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringArray = stringList.toArray(new String[]{});
		
		int p = Integer.parseInt(stringArray[2]);
		return p;
		
	}

}
