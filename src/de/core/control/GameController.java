package de.core.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import de.core.level.Level;
import de.core.Gameboard;
import de.core.audio.BackgroundMusic;
import de.core.control.GameStatus;
import de.ui.GamePauseView;
import de.ui.GameEndView;
import de.ui.GameView;

/**
 * controls fontends & backend
 * implements frontends GameEndView & GameView; implements backends Level & Gameboard
 */
public class GameController implements ActionListener {
    // ui components
    private String frameTitle = "Gamon";
    private JFrame frame;
    private GameView gameView; 
    private GamePauseView pauseView;
    private boolean pauseViewOpen = false;
    
    // logical components
    private KeyInput keyInput;
    private Gameboard gameboard; 
    private Timer gameLoopTimer;
    private BackgroundMusic backgroundMusic;

    // deep copy for restarting
    private Level initialLevel; 

    // time measurements
    private long startTimeMillis;

    // for closing/exiting game
    private GameStatus gameStatus;

    /**
     * creates base frame & sets Gameboard to choosen Level for changing Level data & GameView for reading Level data
     * GameView (fontend) draws Level data that is edited by Gameboard (backend)
     * @param level choosen one as base for Gameboard & GameView objs
     */
    public GameController(Level level, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        
        gameboard = new Gameboard(level);
        gameView = new GameView(level); 
        keyInput = new KeyInput();
        initialLevel = new Level(level);
        startTimeMillis = System.currentTimeMillis(); // for timeMeasurement(); returns used time per action
        backgroundMusic = new BackgroundMusic();

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
        gameLoopTimer = new Timer(60, this);
        gameLoopTimer.start();
    }

    /**
     * will be executed after each performed action
     * calls methods for movement after KeyListener has returned a direction that is valid
     * decreases time left in Gameboard
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // pause handling
        if (keyInput.getIsPaused()) {
            if (!pauseViewOpen) {
                gameLoopTimer.stop();
                pauseView = new GamePauseView(this, frame);
                pauseViewOpen = true;
            }
            return;
        } else {
            if (pauseViewOpen) {
                pauseView.dispose();
                pauseViewOpen = false;
                startTimeMillis = System.currentTimeMillis(); // reset time delta
                gameLoopTimer.start();
            }
        }

        // conditions & statistics
        long timeUsed = getTimeMeasurement();
        // part of easteregg -> disables all 
        if (!keyInput.getEastereggStatus()) {
            gameboard.decreaseTimeLeft(timeUsed);
            gameboard.addTimeUsed(timeUsed);
        }

        // movement
        Direction dir = keyInput.getDirection();

        // player inputs something
        if (dir != null) {
            if (!keyInput.getEastereggStatus()) {
                gameboard.decreaseStepsLeft(); gameboard.addStepsUsed();
            }
            gameboard.moveTo(dir);      
        }

        if (gameboard.getEndGame()) {
            gameLoopTimer.stop();
            frame.dispose(); 
            GameOver(gameboard.getStatus());
            return;
        }
        
        // redraw & add informations
        updateViewStats(); // New method to pass stats to GameView
        frame.setTitle(frameTitle);
        gameView.setEastereggStatus(keyInput.getEastereggStatus());
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
     * Displays the end screen (GameEndView) and closes the game window.
     * @param status True if the level was completed, False otherwise.
     */
    public void GameOver(boolean status) {
        if (gameLoopTimer != null && gameLoopTimer.isRunning()) {
            gameLoopTimer.stop();
        }

        int totalStepsUsed = gameboard.getStepsUsed();
        double totalTimeUsed = gameboard.getTimeUsed();
        
        frame.dispose(); 
        backgroundMusic.stop();
        
        new GameEndView(status, totalStepsUsed, totalTimeUsed, this);
    }

    public void gameRestart() {
        if (gameLoopTimer != null) {
            gameLoopTimer.stop();
        }

        if (frame != null) {
            frame.dispose();
        }

        Level nextInitialLevel = new Level(initialLevel); 
        backgroundMusic.stop();
        new GameController(nextInitialLevel, gameStatus);
    }

    /**
     * Resume the game from the pause menu.
     * This method is called by the pause view (button or ESC) and
     * will flip the KeyInput pause flag and restart the timer/view state here.
     */
    public void resumeFromPause() {
        // if not paused nothing to do
        if (keyInput.getIsPaused()) { keyInput.togglePause(); }
        // close pause view if still open
        if (pauseViewOpen) {
            if (pauseView != null) {
                pauseView.dispose();
                pauseView = null;
            }
            pauseViewOpen = false;
        }

        // reset time base to avoid huge delta and start timer
        startTimeMillis = System.currentTimeMillis();
        if (gameLoopTimer != null && !gameLoopTimer.isRunning()) {
            gameLoopTimer.start();
        }
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus.setGameStatus(gameStatus);
    }

    public boolean getGameStatus() {
        return gameStatus.getGameStatus();
    }

    public void setBackToMenu(boolean backToMenu) {
        this.gameStatus.setBackToMenu(backToMenu);
    }

    public boolean getBackToMenu() {
        return gameStatus.getBackToMenu();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }
}