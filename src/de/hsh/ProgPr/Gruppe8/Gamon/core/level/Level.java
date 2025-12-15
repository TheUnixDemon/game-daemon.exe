package de.hsh.ProgPr.Gruppe8.Gamon.core.level;

import java.util.*;
import java.awt.*;

/**
 * definition of maps as Level
 * relocation of moveable objects will be made there through using array/arraylists
 */
public class Level extends Map {
    private int maxSteps = -1; // movement limit
    private long maxTime = -1L; // time limit in secounds
    private Point player; // position of player
    private ArrayList<Point> obstacles;
    private ArrayList<Point> walls;
    private ArrayList<Point> goals;

    /**
     * reads ascii map and creates of ArrayList<> for ascii items
     * @param filename full path to ascii map
     */
    public Level(String filename) {
        super(filename);
        setLevel();
    }

    /**
     * reads ascii map and creates of ArrayList<> for ascii items
     * @param filename full path to ascii map
     */
    public Level(Level level) {
        super(level.getMap());
        this.setMaxSteps(level.maxSteps);
        this.setMaxTime(level.maxTime);
        setLevel();
    }

    public void setLevel() {
        this.obstacles = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.goals = new ArrayList<>();

        // scanning ascii map after obstacles, walls & others
        ArrayList<char[]> map = super.getMap(); 
        for (int i = 0; i < map.size(); i++) {
            char[] line = map.get(i);
            for (int j = 0; j < line.length; j++) {
                switch (line[j]) {
                    case '*':
                        this.obstacles.add(new Point(i, j));
                        break;
                    case 'X':
                        this.walls.add(new Point(i, j));
                        break;
                    case '.':
                        this.goals.add(new Point(i, j));
                        break;
                    case '@':
                        this.player = new Point(i, j);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * @return player
     */
    public Point getPlayer() {
        return player;
    }

    /**
     * returns obstacle ArrayList<Point>; reference used to move obstacles
     * @return obstacles
     */
    public ArrayList<Point> getObstacles() {
        return obstacles;
    }

    /**
     * returns wall ArrayList<Point>
     * @return walls
     */
    public ArrayList<Point> getWalls() {
        return walls;
    }

    /**
     * returns goals ArrayList<Point>
     * @return goals
     */
    public ArrayList<Point> getGoals() {
        return goals;
    }

    /**
     * @return maxSteps
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * @return maxTime
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * change player position
     * @param player
     */
    public void setPlayer(Point player) {
        this.player = player;
    }

    /**
     * replace obstacles ArrayList<Point>
     * @param obstacles
     */
    public void setObstacles(ArrayList<Point> obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * replace maxSteps int
     * @param maxSteps
     */
    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    /**
     * replace maxTime int
     * @param maxTime
     */
    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }
}