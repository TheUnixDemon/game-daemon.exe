package de.core.control;

import de.core.level.Level;
import de.core.Gameboard; 
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.util.ArrayList;


public class GameController implements ActionListener {
    
    // UI komponents
    private JFrame frame;
    private GameView gameView; 
    
    // Logik komponents
    private KeyInput keyInput;
    private Gameboard gameboard; 
    private Timer gameLoopTimer;

    public GameController(Level level) {
        this.gameboard = new Gameboard(level); 
        
        this.gameView = new GameView(level); 
        this.keyInput = new KeyInput();

        frame = new JFrame("test" + gameboard.getStepsLeft());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true); 

        frame.add(gameView);
        gameView.setFocusable(true);
        gameView.addKeyListener(keyInput);

        //frame.addKeyListener(keyInput);
        //frame.setFocusable(true);
        //frame.requestFocusInWindow();

        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        gameView.requestFocusInWindow();

        // fix for repeated input
        gameLoopTimer = new Timer(128, this);
        gameLoopTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Direction dir = keyInput.getDirection();

        if (dir != null) {
            gameboard.moveTo(dir);       
            frame.setTitle("test test" + gameboard.getStepsLeft());
        }

        if (gameboard.getStatus()) {
            gameLoopTimer.stop();
            JOptionPane.showMessageDialog(frame, "won");
            frame.dispose(); 
            return;
        }
        gameView.repaint();
    }
}