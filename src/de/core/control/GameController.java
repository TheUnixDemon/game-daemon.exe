package de.core.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import de.core.level.Level;
import de.core.Gameboard; 
import de.ui.GameView;

/**
 * comment here are missing :(
 */
public class GameController implements ActionListener {
    // ui components
    private String frameTitle = "Gamon";
    private JFrame frame;
    private GameView gameView; 
    
    // logical components
    private KeyInput keyInput;
    private Gameboard gameboard; 
    private Timer gameLoopTimer;

    // time measurements
    private long startTimeMillis;

    /**
     * creates base frame & sets Gameboard to choosen Level for changing Level data & GameView for reading Level data
     * GameView (fontend) draws Level data that is edited by Gameboard (backend)
     * @param level choosen one as base for Gameboard & GameView objs
     */
    public GameController(Level level) {
        this.gameboard = new Gameboard(level);
        this.gameView = new GameView(level); 
        this.keyInput = new KeyInput();
        this.startTimeMillis = System.currentTimeMillis(); // for timeMeasurement(); returns used time per action

        // base frame
        frame = new JFrame("Gamon" + gameboard.getStepsLeft());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true); 

        frame.add(gameView);
        gameView.setFocusable(true);
        gameView.addKeyListener(keyInput);

        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        gameView.requestFocusInWindow();

        // set for fixed refresh rate
        gameLoopTimer = new Timer(12, this);
        gameLoopTimer.start();
    }

    /**
     * will be executed after each performed action
     * calls methods for movement after KeyListener has returned a direction that is valid
     * decreases time left in Gameboard
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // conditions & statistics
        if (gameboard.getEndGame()) {}
        long timeUsed = getTimeMeasurement();
        gameboard.decreaseTimeLeft(timeUsed); gameboard.addTimeUsed(timeUsed);


        // movement
        Direction dir = keyInput.getDirection();

        // player inputs something
        if (dir != null) {
            gameboard.decreaseStepsLeft(); gameboard.addStepsUsed();
            gameboard.moveTo(dir);       
            frame.setTitle(frameTitle + " time used: " + gameboard.getTimeUsed());
        }

        if (gameboard.getStatus()) {
            gameLoopTimer.stop();
            JOptionPane.showMessageDialog(frame, "won");
            frame.dispose(); 
            return;
        }
        
        // redraw & add informations
        updateViewStats(); // New method to pass stats to GameView
        frame.setTitle(frameTitle);
        gameView.repaint();
    }

    /**
     * Gathers current game statistics and passes them to GameView for drawing.
     */
    private void updateViewStats() {
        int stepsLeft = gameboard.getStepsLeft();
        double timeLeft = gameboard.getTimeLeft();
        
        // draw stats on gameboard ui
        gameView.updateStats(stepsLeft, timeLeft);
    }

    /**
     * @return time span between last end current actions
     * used for decreasing Gameboard.timeLeft() and adding to Gameboard.timeUsed()
     */
    public long getTimeMeasurement() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = System.currentTimeMillis() - startTimeMillis;
        startTimeMillis = currentTime;
        return deltaTime;
    }
    
    /**
     * will be executed after endGame is set to True by Gameboard
     */
    public void GameOver() {}
}