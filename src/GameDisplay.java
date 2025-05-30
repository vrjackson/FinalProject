/**
 * This class handles the window the game operates within.
 * @author Ram R.
 * @author Tori J.
 * @author Emily H.
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameDisplay extends JPanel{

    private String userName;
    private int battlesWon, battlesLost;
    private Image backgroundImage, characterSprite;
    private ImageIcon saveIcon;
    private int xCoord = 400, yCoord = 300;
    private final int characterWidth = 25, characterHeight = 25;
    private final int frameWidth = 800, frameHeight = 600;
    private final int moveSpeed = 10;
    private int bgWidth, bgHeight;
    private JButton saveButton;
    private CardLayout cardLayout;
    private JPanel parent;
    private Timer battleTimer;
    private JLabel stats;


    /**
     * Constructor that loads in saved data/new game with parameters
     * @param cardLayout the CardLayout that manages screen switching
     * @param parent holds the pages/screens(main world/battle)
     * @param userName name chosen by user
     * @param xCoord for character position in window
     * @param yCoord for character position in window
     * @param battlesWon stores number of battles won
     * @param battlesLost stores number of battles lost
     */
    public GameDisplay(CardLayout cardLayout, JPanel parent, String userName, int xCoord, int yCoord, int battlesWon, int battlesLost){
        this(cardLayout, parent);
        this.userName = userName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.battlesWon = battlesWon;
        this.battlesLost = battlesLost;
        updateStats();
    }

    /**
     * This default constructor is used to build the window the game operates in. It sets the background,
     * the character sprite, the movement keys and speed, and a save icon to click and save progress.
     */
    public GameDisplay(CardLayout cardLayout, JPanel parent) {

        this.cardLayout = cardLayout;
        this.parent = parent;

        backgroundImage = new ImageIcon("Project_Background_Horror.png").getImage();
        characterSprite = new ImageIcon("characterSprite.png").getImage();
        saveIcon = new ImageIcon("save.png");

        Image scaledImage = saveIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        bgWidth = backgroundImage.getWidth(null);
        bgHeight = backgroundImage.getHeight(null);

        //sets focus in window to register key presses for movement
        setFocusable(true);
        requestFocusInWindow();

        //formatting the save button
        saveButton = new JButton(scaledIcon);
        saveButton.setBounds(700, 0, 100, 100);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(_ -> saveGame());
        this.add(saveButton);

        //formating the stats/HUD
        stats = new JLabel();
        stats.setForeground(Color.WHITE);
        stats.setFont(new Font("Comic Sans", Font.BOLD, 14));
        setLayout(null);
        stats.setBounds(15, 10, 300, 20);
        this.add(stats);
        updateStats();

        //movement keys and repaints the screen if after a key press
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A: //Left
                        characterSprite = new ImageIcon("characterSprite.png").getImage();
                        xCoord -= moveSpeed;
                        break;
                    case KeyEvent.VK_D: //Right
                        characterSprite = new ImageIcon("characterSpriteR.png").getImage();
                        xCoord += moveSpeed;
                        break;
                    case KeyEvent.VK_W: //Up
                        yCoord -= moveSpeed;
                        break;
                    case KeyEvent.VK_S: //Down
                        yCoord += moveSpeed;
                        break;
                }
                repaint();
            }
        });
    }

    /**
     * Overrides the paintComponent method to handle the scrolling background and to fix the character sprite to the
     * center of the frame. It also calculates the camera view based on the character's x and y coordinates, and
     * ensures the camera doesn't exceed the background/frame bounds
     * @param graphics the <code>Graphics</code> object to protect; used for rendering the background and character sprite
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int cameraX = xCoord - frameWidth / 2;
        int cameraY = yCoord - frameHeight / 2;

        cameraX = Math.max(0, Math.min(cameraX, bgWidth - frameWidth));
        cameraY = Math.max(0, Math.min(cameraY, bgHeight - frameHeight));

        graphics.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight,
                cameraX, cameraY, cameraX + frameWidth, cameraY + frameHeight, this);

        int drawX = frameWidth / 2 - characterWidth / 2;
        int drawY = frameHeight / 2 - characterHeight / 2;
        graphics.drawImage(characterSprite, drawX, drawY, characterWidth, characterHeight, this);

    }

    /**
     * this method is used within an action listener to prompt the user that their progress has been saved,
     * then ensures this(GameDisplay object) regains focus after closing the popup
     */
    public void saveGame(){
        Save.saveGame(userName, xCoord, yCoord, battlesWon, battlesLost);
        JOptionPane.showMessageDialog(this, "Game has been saved.");
        this.requestFocusInWindow();
    }

    /**
     * this method has a 50/50 change to initiate a battle every 10 seconds
     */
    public void startBattleTimer() {
        battleTimer = new Timer(10000, _ -> {//10 seconds, then lambda as action listener
            if (Math.random() < 0.5) {//RNG a double between 0.0 to 1.0/test if greater than 0.5
                cardLayout.show(parent, "battle");//switches to battle page
            }
        });
        battleTimer.start();//starts the timer
    }

    /**
     * this method increments the battlesWon variable if the user won a fight
     */
    public void ifBattleWon() {
        this.battlesWon++;
        updateStats();
    }

    /**
     * this method increments the battlesLost variable if the user lost a fight
     */
    public void ifBattleLost() {
        this.battlesLost++;
        updateStats();
    }

    /**
     * this method switches the screen/page back to the main game
     */
    public void battleOver() {
        cardLayout.show(parent, "world");
        this.requestFocusInWindow();
    }

    /**
     * this method formats the HUD with the players info to be displayed live(userBame/wins/losses)
     */
    public void updateStats() {
        stats.setText(userName + " | Wins: " + battlesWon + " | Losses: " + battlesLost);
    }

    /**
     * returns the frameWidth upon request
     * @return frameWidth value
     */
    public int getFrameWidth() {
        return this.frameWidth;
    }

    /**
     * returns the frameHeight upon request
     * @return frameHeight value
     */
    public int getFrameHeight() {
        return this.frameHeight;
    }
}