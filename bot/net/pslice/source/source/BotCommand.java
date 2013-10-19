package source;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class BotCommand {
	
	static Charset charset = Charset.defaultCharset();
	
	public static String[] getCommandList(){
		File userFile = new File("Files/properties.commands.txt");
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(userFile.toPath(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringArray = stringList.toArray(new String[]{});
		return stringArray;
	}

	public String getCommandInfo(String command){
		
		return null;
	}
}
