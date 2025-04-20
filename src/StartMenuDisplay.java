/**
 * this class handles the startup menu and lets the user begin a new game/load their save file/
 * overwrite their save with fresh data(new game)
 * @author Ram R.
 * @author Tori J.
 * @author Emily H.
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class StartMenuDisplay extends JPanel {

    private Image backGround;

    /**
     * constructor for the startup menu. checks if a save file already exists. If it doesn't the only option will be to
     * start a new game, if it does exist the player can choose to either load their save or start a new game(erasing
     * their data)
     * @param cardLayout
     * @param cards
     * @param frame
     */
    public StartMenuDisplay(CardLayout cardLayout, JPanel cards, JFrame frame) {

        backGround = new ImageIcon("MenuBackGround.jpg").getImage();
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        //logic for when there is an existing save file
        if (Save.saveFileExists()) {
            JButton loadGame = new JButton("Load Game");
            JButton newGame = new JButton("Start New Save");

            //loads data from the Save class method "loadData()"
            loadGame.addActionListener(e -> {
                String[] savedData = Save.loadGame();
                String userName = savedData[0];
                int xCoord = Integer.parseInt(savedData[1]);
                int yCoord = Integer.parseInt(savedData[2]);
                int battlesWon = Integer.parseInt(savedData[3]);
                int battlesLost = Integer.parseInt(savedData[4]);

                //data passed into constructors accordingly
                GameDisplay gamePanel = new GameDisplay(cardLayout, cards, userName, xCoord, yCoord, battlesWon, battlesLost);
                BattleDisplay battlePanel = new BattleDisplay(cardLayout, cards, gamePanel);

                cards.add(gamePanel, "world");
                cards.add(battlePanel, "battle");
                cardLayout.show(cards, "world");
                frame.revalidate();
                gamePanel.requestFocusInWindow();//without this line, key presses won't register, rendering the game unplayable! focusing is fun
                gamePanel.startBattleTimer();
            });

            newGame.addActionListener(e -> startNewGame(cardLayout, cards, frame));

            buttonPanel.add(loadGame);
            buttonPanel.add(newGame);
        } else {//logic for when there isn't a previously saved file, to start a new game is the only option
            JButton newGame = new JButton("New Game");
            newGame.addActionListener(e -> startNewGame(cardLayout, cards, frame));
            buttonPanel.add(newGame);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * this method gets called to action when the button "Start New Game" is selected. it overwrites the existing data
     * with the players new username, and predetermined variables. Then proceeds to gameplay/world
     * @param cardLayout
     * @param cards
     * @param frame
     */
    private void startNewGame(CardLayout cardLayout, JPanel cards, JFrame frame) {
        JOptionPane gameSelect = new JOptionPane();
        String userName = gameSelect.showInputDialog(this, "What do ya call yerself?!", "New Game", QUESTION_MESSAGE);

        if (userName == null) { // If the user selects cancel, the select screen will close
            gameSelect.setVisible(false);
        } else { // The new game will start when OK is pressed
            //default data that overwrites previously saved data
            int xCoord = 400, yCoord = 300, battlesWon = 0, battlesLost = 0;
            Save.saveGame(userName, xCoord, yCoord, battlesWon, battlesLost);

            //data passed into constructors accordingly
            GameDisplay gamePanel = new GameDisplay(cardLayout, cards, userName, xCoord, yCoord, battlesWon, battlesLost);
            BattleDisplay battlePanel = new BattleDisplay(cardLayout, cards, gamePanel);

            cards.add(gamePanel, "world");
            cards.add(battlePanel, "battle");
            cardLayout.show(cards, "world");
            frame.setContentPane(cards);
            frame.revalidate();
            gamePanel.requestFocusInWindow();
            gamePanel.startBattleTimer();
        }
    }

    /**
     * Overrides the paintComponent method to render the background, scaled into the full frame
     * @param g the <code>Graphics</code> object to protect; used for rendering the background
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
    }
}
