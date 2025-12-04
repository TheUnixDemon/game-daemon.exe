package de.core.level;

import java.util.*;
import java.awt.*;

/**
 * definition of maps as Level
 * relocation of moveable objects will be made there through using array/arraylists
 */
public class Level extends Map {
    private int maxSteps; // movement limit
    private int maxTime; // time limit in secounds
    private Point player; // position of player
    private ArrayList<Point> obstacles;
    private ArrayList<Point> walls;
    private ArrayList<Point> goals;

    /**
     * reads ascii map and creats groups of arraylists for each ascii item
     * @param filename full path to ascii map
     * @throws Exception
     */
    public Level(String filename) throws Exception {
        super(filename);
        this.obstacles = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.goals = new ArrayList<>();

        // scanning ascii map after obstacles, walls & others
        ArrayList<char[]> map = super.getMap(); 
        for (int i = 0; i < map.size(); i++) {
            char[] line = map.get(i);
            for (int j = 0; j < line.length; j++) {
                switch (line[j]) {
                    case '$':
                        this.obstacles.add(new Point(i, j));
                        break;
                    case '#':
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
    public Point getPlayer() {
        return player;
    }
    public ArrayList<Point> getObstacles() {
        return obstacles;
    }
    public ArrayList<Point> getWalls() {
        return walls;
    }
    public ArrayList<Point> getGoals() {
        return goals;
    }
    public int getMaxSteps() {
        return maxSteps;
    }
    public int getMaxTime() {
        return maxTime;
    }
    public void setPlayer(Point player) {
        this.player = player;
    }
    public void setObstacles(ArrayList<Point> obstacles) {
        this.obstacles = obstacles;
    }
    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}