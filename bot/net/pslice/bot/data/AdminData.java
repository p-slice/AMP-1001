package net.pslice.bot.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class AdminData {

    private static final Charset charset = Charset.defaultCharset();

    public static boolean checkLanguage(String message) {
        Path filePath = new File("Files/properties.language.txt").toPath();
        List<String> stringList = null;
        try {
            stringList = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stringList != null;
        String[] stringArray = stringList.toArray(new String[stringList.size()]);
        boolean containsLanguage = false;
        for (String aStringArray : stringArray) {
            if (message.contains(aStringArray)) {
                containsLanguage = true;
                break;
            }
        }
        return containsLanguage;
    }
}
