package net.pslice.bot.source;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {

    private static final Charset charset = Charset.defaultCharset();

    public static String lineText(String fileName, int n) {
        fileName = "Files/" + fileName + ".txt";
        Path filePath = new File(fileName).toPath();
        List<String> stringList = null;
        try {
            stringList = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stringList != null;
        return stringList.toArray(new String[stringList.size()])[n];
    }

    public static String[] wholeText(String fileName) {
        fileName = "Files/" + fileName + ".txt";
        Path filePath = new File(fileName).toPath();
        List<String> stringList = null;
        try {
            stringList = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stringList != null;
        return stringList.toArray(new String[stringList.size()]);
    }
}
