/**
 * This program is a game where the player roams an open world and encounters enemies they must defeat, or fear...
 * @author Ram R.
 * @author Tori J.
 * @author Emily H.
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Stardew Valley From Temu");
        CardLayout cardLayout = new CardLayout();//stores out pages(world/battle)
        JPanel cards = new JPanel(cardLayout);//sets the page viewed

        StartMenuDisplay menuDisplay = new StartMenuDisplay(cardLayout, cards, frame);
        GameDisplay gamePanel = new GameDisplay(cardLayout, cards);
        BattleDisplay battlePanel = new BattleDisplay(cardLayout, cards, gamePanel);

        frame.setSize(gamePanel.getFrameWidth(), gamePanel.getFrameHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cards.add(menuDisplay, "menu");
        cards.add(gamePanel, "world");
        cards.add(battlePanel, "battle");
        cardLayout.show(cards, "menu");

        frame.setContentPane(cards);
        frame.setVisible(true);
    }
}