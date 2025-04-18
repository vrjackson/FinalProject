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
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Trouble has found you!", SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JPanel battleOptions = new JPanel(new FlowLayout());

        ImageIcon enemyIcon = new ImageIcon("enemySprite.png");
        JLabel enemyLabel = new JLabel(enemyIcon);
        enemyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enemyLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(enemyLabel, BorderLayout.CENTER);

        //when fight is selected, there is a 50/50 chance of win/loss
        JButton fight = new JButton("Hit!");
        fight.addActionListener( e -> {//using lambda syntax cus it feels cool
            boolean win = Math.random() < 0.5;
            //the line below is conditional and determined by if "win"(above) is true/false
            JOptionPane.showMessageDialog(this, win ? "Victory!" : "Bro thought he had aura...");
            gamePanel.battleOver();
        });

        //when run is selected, the player will always escape from a battle
        JButton run = new JButton("Run!");
        run.addActionListener( e -> {//using lambda syntax cus it feels cool
            JOptionPane.showMessageDialog(this, "You escaped safely");
            gamePanel.battleOver();
        });

        battleOptions.add(fight);
        battleOptions.add(run);
        add(battleOptions, BorderLayout.SOUTH);
    }
}
