package source;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.pircbotx.*;

public class UserInfo {
	
	static Charset charset = Charset.defaultCharset();
	
	public String userNick;
	public String userLogin;
	public String userHostMask;
	
	public UserInfo(User user){
		this.userNick = user.getNick();
		this.userLogin = user.getLogin();
		this.userHostMask = user.getHostmask();
	}
	
	public int getPerms(){
		Path filePath = new File(userNick + ".txt").toPath();
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(filePath, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringArray = stringList.toArray(new String[]{});
		
		int p = Integer.parseInt(stringArray[2]);
		return p;
		
	}

}
