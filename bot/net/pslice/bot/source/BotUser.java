package net.pslice.bot.source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class BotUser {

    private static final Charset charset = Charset.defaultCharset();

    private final File userFile;
    private final String[] fileContents;

    public BotUser(String botUser) {
        File userFile = new File("Files/user." + botUser + ".txt");
        this.userFile = userFile;
        if (!userFile.exists())
            this.fileContents = null;
        else {
            List<String> stringList = null;
            try {
                stringList = Files.readAllLines(userFile.toPath(), charset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert stringList != null;
            this.fileContents = stringList.toArray(new String[stringList.size()]);
        }
    }

    public String getNick() {
        if (!userFile.exists())
            return "";
        else
            return fileContents[0];
    }

    public int getRank() {
        if (!userFile.exists())
            return 0;
        else
            return Integer.parseInt(fileContents[2]);
    }

    public boolean hasRank(int r) {
        boolean hasRank = false;
        if (r == getRank())
            hasRank = true;
        return hasRank;
    }

    public void setRank(String newRank) {
        String editLine = fileContents[2];

        editLine = editLine.replace(fileContents[2], newRank);
        String content = null;
        try {
            content = new String(Files.readAllBytes(userFile.toPath()), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert content != null;
        content = content.replaceAll(fileContents[2], editLine);
        try {
            Files.write(userFile.toPath(), content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNick(String newNick) {
        String editLine = fileContents[0];

        editLine = editLine.replace(fileContents[0], newNick);
        String content = null;
        try {
            content = new String(Files.readAllBytes(userFile.toPath()), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert content != null;
        content = content.replaceAll(fileContents[0], editLine);
        try {
            Files.write(userFile.toPath(), content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String newUser) {
        boolean isUser = false;
        if (userFile.exists())
            isUser = true;
        else {
            try {
                FileWriter fileWriter = new FileWriter(userFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


                bufferedWriter.append(newUser);
                bufferedWriter.newLine();
                bufferedWriter.append("//");
                bufferedWriter.newLine();
                bufferedWriter.append("1");
                bufferedWriter.close();

                isUser = false;
            } catch (IOException ex) {
                System.out.println("Error writing to file '" + userFile + "'");
            }
        }
        return isUser;
    }
}
