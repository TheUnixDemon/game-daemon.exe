package de.core;

import java.util.*;
import javax.swing.SwingUtilities;

import de.core.level.Level;
import de.core.level.LevelCreate;
import de.core.control.GameController;
import de.ui.*;

/**
 * control class and entrypoint for game
 */
public class Gamecore {
    private ArrayList<Level> levels = new ArrayList<>();
    
    /**
     * entrypoint in game
     */
    public Gamecore() {
        // create & save Level objects as arraylist in local reference
        LevelCreate levelCreate = new LevelCreate();
        levels = levelCreate.getLevels();

        // start ui
        ui();
    }

    /**
     * start ui
     */
    public void ui() {
        LevelSelectionView levelSelectionView = new LevelSelectionView(levels);
    }
}