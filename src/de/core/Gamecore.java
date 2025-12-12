package de.core;

import java.util.*;

import javax.swing.SwingUtilities;

import de.core.level.Level;
import de.ui.*;

/**
 * here the entry for everything
 */
public class Gamecore {
    private ArrayList<Level> levels = new ArrayList<>();
    
    public Gamecore() {
        prepareLevels();
        SwingUtilities.invokeLater(() -> { new LevelSelectionView(levels); });
    }

    /**
     * creates levels and sets them into the arraylist
     */
    public void prepareLevels() {
        levels.add(createLevel("map-1.txt", 100, 50000));
        levels.add(createLevel("map-2.txt", 40, 30000));
        levels.add(createLevel("map-3.txt", 30,30000));
    }

    /**
     * used for manually hardcoded maploader
     * @param filename of map within right folder
     * @param maxSteps of level - condition
     * @param maxTime of level - condition
     * @return level object for arraylist
     */    
    private Level createLevel(String filename, int maxSteps, long maxTime) {
        String basePath = "./srv/level/map/"; // relative path form execution
        Level level = new Level(basePath + filename);
        if (maxSteps != -1) { level.setMaxSteps(maxSteps); }
        if (maxTime != -1) { level.setMaxTime(maxTime); }
        return level;
    }
}