package net.pslice.bot.managers;

import java.io.*;

public final class FileManager {

    /*
     * ===========================================
     * Variables, Objects, Lists, Sets and Maps:
     * ===========================================
     */

    private final String

            // The main directory to where bot-related files are saved
            directory;





    /**
     * ===========================================
     * Initializer:
     *
     * @param directory: The path to where bot-related files are saved
     * ===========================================
     */

    public FileManager(String directory)
    {
        this.directory = directory;
    }





    /**
     * ===========================================
     * Method to save a file:
     *
     * @param object: The object being saved
     * @param name: The name of the file
     * ===========================================
     */

    public <O> void save(O object, String name)
    {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(String.format("%s/%s.bin", directory, name)));
            output.writeObject(object);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * ===========================================
     * Method to load a file:
     *
     * @param name: The name of the file
     * @return The contents of the file
     * The method performs an unchecked cast to the loaded
     *      object.
     * ===========================================
     */

    @SuppressWarnings("unchecked")
    public <O> O load(String name)
    {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(String.format("%s/%s.bin", directory, name)));
            O result = (O)input.readObject();
            input.close();
            return result;
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    /**
     * ===========================================
     * Getter for the existence of a file:
     *
     * @param name: The name of the file being checked
     * @return Whether or not the file exists
     * ===========================================
     */

    public boolean fileExists(String name)
    {
        return new File(String.format("%s/%s.bin", directory, name)).exists();
    }

}
