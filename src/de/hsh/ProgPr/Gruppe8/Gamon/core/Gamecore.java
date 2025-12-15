package de.hsh.ProgPr.Gruppe8.Gamon.core;

import java.util.*;
import javax.swing.SwingUtilities;

import de.hsh.ProgPr.Gruppe8.Gamon.core.level.Level;
import de.hsh.ProgPr.Gruppe8.Gamon.core.level.LevelCreate;
import de.hsh.ProgPr.Gruppe8.Gamon.core.control.GameController;
import de.hsh.ProgPr.Gruppe8.Gamon.core.control.GameStatus;
import de.hsh.ProgPr.Gruppe8.Gamon.ui.*;

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