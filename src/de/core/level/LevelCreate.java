package de.core.level;

import java.util.ArrayList;

/**
 * class for manually creating hard coded levels for game
 */
public class LevelCreate {
    private ArrayList<Level> levels = new ArrayList<>();

    /**
     * creates Levels & add them to ArrayList for getting through getLevels() later on
     */
    public LevelCreate() {
        addLevels();
    }

    /**
     * @return arraylist of Level objects - for Level Selection Menu
     */
    public ArrayList<Level> getLevels() {
        return levels;
    }

    /**
     * creates levels and sets them into the arraylist
     */
    protected void addLevels() {
        levels.add(createLevel("map-0.txt", 120, 120000));
        levels.add(createLevel("map-1.txt", 120, 120000));
        levels.add(createLevel("map-2.txt", 120, 120000));
        levels.add(createLevel("map-3.txt", 120, -1));
        levels.add(createLevel("map-4.txt", -1, 120000));
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