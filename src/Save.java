import java.io.PrintWriter;
import java.util.Scanner;

public class Save {
    private String username;
    private int xCoord;
    private int yCoord;
    private int battlesWon;
    private int battlesLost;
    private boolean hasSaved;

    public Save(String username, Scanner in) {
        this.username = username;
        xCoord = Integer.parseInt(in.nextLine());
        yCoord = Integer.parseInt(in.nextLine());
        battlesWon = Integer.parseInt(in.nextLine());
        battlesLost = Integer.parseInt(in.nextLine());
        hasSaved = false;
    }

    public Save(String username) {
        this.username = username;
        xCoord = 100;
        yCoord = 100;
        battlesWon = 0;
        battlesLost = 0;
        hasSaved = false;
    }

    public String getUsername() {
        return username;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public int getBattlesLost() {
        return battlesLost;
    }

    public void update(PrintWriter out) {
        // Username is not here, I'm under the assumption that won't have to be changed
        // Assuming that there's something to scan the file for the certain username
        out.println(xCoord);
        out.println(yCoord);
        out.println(battlesWon);
        out.println(battlesLost);
        out.println(hasSaved);
    }
}
