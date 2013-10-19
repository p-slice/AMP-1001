package data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdminData {
	
	static Charset charset = Charset.defaultCharset();
	
	public static boolean checkLanguage(String message){
		Path filePath = new File("Files/properties.language.txt").toPath();
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(filePath, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringArray = stringList.toArray(new String[]{});
		boolean containsLanguage = false;
		for (int i = 0; i < stringArray.length; i++){
			if (message.contains(stringArray[i])){
				containsLanguage = true;
				break;
			}				
		}
		return containsLanguage;
	}

}
