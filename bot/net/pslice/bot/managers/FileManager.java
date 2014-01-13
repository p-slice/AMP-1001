package net.pslice.bot.managers;

import java.io.*;

public final class FileManager {

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    private FileManager(){}

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static <O> void save(O object, String path)
    {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(String.format("Files/%s.bin",  path)));
            output.writeObject(object);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <O> O load(String path)
    {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(String.format("Files/%s.bin", path)));
            O result = (O)input.readObject();
            input.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * ===========================================
    * ...
    * ===========================================
     */

    public static boolean fileExists(String path)
    {
        File file = new File(String.format("Files/%s.bin", path));
        return file.exists();
    }

}
