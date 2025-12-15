package de.core;

import java.util.*;
import javax.swing.SwingUtilities;

import de.core.level.Level;
import de.core.level.LevelCreate;
import de.core.control.GameController;
import de.core.control.GameStatus;
import de.ui.*;

/**
 * control class and entrypoint for game
 */
public class Gamecore {    
    // game ui & game status for controlling
    private LevelSelectionView levelSelectionView;
    private GameStatus gameStatus;
    
    /**
     * entrypoint in game
     */
    public Gamecore() {
        handleGame();
    }

    /**
     * start ui & check isAlive
     */
    public void handleGame() {
        while (true) { 
            // loading levels out of ascii text file
            LevelCreate levelCreate = new LevelCreate();
            ArrayList<Level> levels = levelCreate.getLevels();
            
            // creating default gameStatus, creating ui instances & controlling
            gameStatus = new GameStatus();
            levelSelectionView = new LevelSelectionView(levels, gameStatus);
            while (true) {
                System.out.println("gameStatus: " + gameStatus.getGameStatus() + ", backToMenu: " + gameStatus.getBackToMenu());
                if (levelSelectionView.getGameStatus() == false) { return; }
                if (levelSelectionView.getBackToMenu() == true) { break; }
                
                // for not calling gameStatus too often
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
}