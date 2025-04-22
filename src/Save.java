/**
 * This class handles the saving/loading operations
 * @author Ram R.
 * @author Tori J.
 * @author Emily H.
 * @version 1.0
 */
import java.io.*;

public class Save {

    private static final String saveFile = "saveFile.txt";

    /**
     * this method overwrites the existing save file with data passed in
     * @param userName name chosen by user
     * @param x for character position in window
     * @param y for character position in window
     * @param battlesWon stores number of battles won
     * @param battlesLost stores number of battles lost
     */
    public static void saveGame(String userName, int x, int y, int battlesWon, int battlesLost) {
        try (PrintWriter saver = new PrintWriter(new FileWriter(saveFile))) {
            saver.println(userName);
            saver.println(x);
            saver.println(y);
            saver.println(battlesWon);
            saver.println(battlesLost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method used to check if a save file exists
     * @return true/false
     */
    public static boolean saveFileExists() {
        File file = new File(saveFile);
        return file.exists();
    }

    /**
     * this method reads in the data from the save file to return it as a String array where needed
     * @return String array containing previously saved data
     */
    public static String[] loadGame() {
        String[] savedData = new String[5];
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            for (int i = 0; i < 5; i++) {
                savedData[i] = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedData;
    }
}
