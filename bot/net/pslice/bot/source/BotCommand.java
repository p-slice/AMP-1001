package net.pslice.bot.source;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BotCommand {

    private static final Charset charset = Charset.defaultCharset();

    public static String[] getCommandList() {
        File userFile = new File("Files/properties.commands.txt");
        List<String> stringList = null;
        try {
            stringList = Files.readAllLines(userFile.toPath(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert stringList != null;
        return stringList.toArray(new String[stringList.size()]);
    }

    public static void setCommandRank(String command, String newRank) {
        String[] commands = FileReader.wholeText("properties.commandsettings");
        for (String command1 : commands) {
            if (command1.startsWith(command)) {
                String newLine = command1.replace(command1.split("[ ]")[1], newRank);
                Path filePath = new File("Files/properties.commandsettings.txt").toPath();
                String content = null;
                try {
                    content = new String(Files.readAllBytes(filePath), charset);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                assert content != null;
                content = content.replaceAll(command1, newLine);
                try {
                    Files.write(filePath, content.getBytes(charset));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public static String getCommandRank(String command) {
        String[] commands = FileReader.wholeText("properties.commandsettings");
        String rank = "0";
        for (String command1 : commands) {
            if (command1.startsWith(command)) {
                rank = command1.split("[ ]")[1];
                break;
            }
        }
        return rank;
    }
}
