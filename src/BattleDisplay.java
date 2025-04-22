/**
 * This class handles switching the game screen into a battle screen within the same window.
 * @author Ram R.
 * @author Tori J.
 * @author Emily H.
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;

public class BattleDisplay extends JPanel {

    /**
     * Constructor for the BattleDisplay screen that populates the frame when a battle occurs.
     * During a battle, the player can either fight or run, should the player fight, they have a 50/50 chance of win/loss.
     * Goes back to game world when battle is over.
     * @param cardLayout the CardLayout that manages screen switching
     * @param parent holds the pages/screens(main world/battle)
     * @param gamePanel a reference to GameDisplay to resume the main world after a scuffle.
     */
    public BattleDisplay(CardLayout cardLayout, JPanel parent, GameDisplay gamePanel) {

        setLayout(null);

        //gets and formats the background; overwrites paintComponent to render the gif scaled to frame, otherwise it'll look ugly...
        ImageIcon battleBackGround = new ImageIcon("BattleBackGround.gif");
        JLabel battleBG = new JLabel(battleBackGround) {
            @Override
            protected void paintComponent(Graphics graphics) {
                graphics.drawImage(battleBackGround.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        battleBG.setBounds(0, 0, 800, 600);
        add(battleBG);

        //formats the message that appears during a battle
        JLabel message = new JLabel("Trouble has found you!", SwingConstants.CENTER);
        message.setFont(new Font("Comic Sans", Font.BOLD, 24));
        message.setForeground(Color.WHITE);
        message.setBounds(200, 75, 400, 40);
        add(message);

        //formats the enemy's sprite
        ImageIcon enemyIcon = new ImageIcon("enemySprite.png");
        JLabel enemyLabel = new JLabel(enemyIcon);
        enemyLabel.setBounds(245,150, enemyIcon.getIconWidth(), enemyIcon.getIconHeight());
        add(enemyLabel);

        //when fight is selected, there is a 50/50 chance of win/loss
        JButton fight = new JButton("Hit!");
        fight.setBounds( 250, 480, 100, 30);
        fight.addActionListener( e -> {//using lambda syntax cus it feels cool
            boolean win = Math.random() < 0.5;
            //the line below is conditional and determined by if "win"(above) is true/false
            JOptionPane.showMessageDialog(this, win ? "YOU WON\nVictory Royale!" : "Ya lost...\nBro thought he had aura");
            if (win == true) {
                gamePanel.ifBattleWon();
            } else {
                gamePanel.ifBattleLost();
            }
            gamePanel.battleOver();
        });

        //when run is selected, the player will always escape from a battle
        JButton run = new JButton("Run!");
        run.setBounds( 450, 480, 100, 30);
        run.addActionListener( e -> {//using lambda syntax cus it feels cool
            JOptionPane.showMessageDialog(this, "You escaped safely!\n..this time..");
            gamePanel.battleOver();
        });

        add(fight);
        add(run);
        setComponentZOrder(battleBG, getComponentCount() -1);//background is moved to the back so buttons/enemies are visible
    }
}
