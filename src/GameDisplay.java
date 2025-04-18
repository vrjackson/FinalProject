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

    private Image backgroundImage, characterSprite;
    private ImageIcon saveIcon;
    private int xCoord = 400, yCoord = 300;
    private int characterWidth = 25, characterHeight = 25;
    private final int frameWidth = 800, frameHeight = 600;
    private final int moveSpeed = 10;
    private int bgWidth, bgHeight;
    private JButton saveButton;
    private CardLayout cardLayout;
    private JPanel parent;
    private Timer battleTimer;

    /**
     * This default constructor is used to build the window the game operates in. It sets the background,
     * the character sprite, the movement keys and speed, and a save icon to click and save progress.
     */
    public GameDisplay(CardLayout cardLayout, JPanel parent) {

        this.cardLayout = cardLayout;
        this.parent = parent;
        backgroundImage = new ImageIcon("Project_Background_1.png").getImage();
        characterSprite = new ImageIcon("characterSprite.png").getImage();
        saveIcon = new ImageIcon("save.png");
        Image scaledImage = saveIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        bgWidth = backgroundImage.getWidth(null);
        bgHeight = backgroundImage.getHeight(null);
        setFocusable(true);
        requestFocusInWindow();

        saveButton = new JButton(scaledIcon);
        saveButton.setBounds(10, 10, 100, 100);
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveGame());
        this.add(saveButton);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A: //Left
                        xCoord -= moveSpeed;
                        break;
                    case KeyEvent.VK_D: //Right
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
        startBattleTimer();
    }

    /**
     * Overrides the paintComponent method to handle the scrolling background and to
     * fix the character sprite to the center of the frame. It also calculates the camera view based on the character's
     * x and y coordinates, and ensures the camera doesn't exceed the background bounds
     * @param graphics the <code>Graphics</code> object to protect; used for drawing the background and character sprite
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
        JOptionPane.showMessageDialog(this, "Game has been saved.");
        this.requestFocusInWindow();
    }

    /**
     * this method has a 50/50 change to initiate a battle every 10 seconds
     */
    private void startBattleTimer() {
        battleTimer = new Timer(10000, e -> {//10 seconds, then lambda as action listener
            if (Math.random() < 0.5) {//RNG a double between 0.0 to 1.0/test if greater than 0.5
                cardLayout.show(parent, "battle");//switches to battle page
            }
        });
        battleTimer.start();//starts the timer
    }

    /**
     * this method switches the screen/page back to the main game
     */
    public void battleOver() {
        cardLayout.show(parent, "world");
        this.requestFocusInWindow();
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

