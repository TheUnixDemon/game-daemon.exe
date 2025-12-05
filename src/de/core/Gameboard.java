package de.core;

import java.awt.*;
import de.core.level.Level;
import de.core.control.Direction;

/**
 * backend for gameplay
 * controls Level object such as movement & game status
 */
public class Gameboard {
    private Level level;
    private int stepsLeft;
    private int timeLeft;
    private boolean status;
    /**
     * sets *Left attributes
     * @param level choosen level to play
     */
    public Gameboard(Level level) {
        this.level = level;
        this.stepsLeft = level.getMaxSteps();
        this.timeLeft = level.getMaxTime();
        this.status = false;
    }
    /**
     * moves objects on gameboard map
     * -> checks if game is won
     * @param dir interpreted input signal as direction
     */
    public void moveTo(Direction dir) {
        if (dir != null) {
            stepsLeft -= 1;
            Point playerTarget = dir.addTo(level.getPlayer());
            // exits method; no realocating
            if (level.getWalls().contains(playerTarget)) { return; }
            // move obstacle to new location
            if (level.getObstacles().contains(playerTarget)) {
                if (!level.getObstacles().contains(dir.addTo(playerTarget)) && !level.getWalls().contains(dir.addTo(playerTarget)) && !level.getGoals().contains(dir.addTo(playerTarget))) {
                    // move object to addTo(playerTarget)
                    int obstacleId = level.getObstacles().indexOf(playerTarget);
                    level.getObstacles().set(obstacleId, dir.addTo(playerTarget));
                }
            }
            // sets status to true to win the game
            if (level.getGoals().contains(playerTarget)) {
                status = true;
            }
            level.setPlayer(playerTarget);
        }
    }
    /**
     * reduces timeLeft attribute
     */
    public void tickSecound() {
        timeLeft -= 1;
    }
    /**
     * @return steps left
     */
    public int getStepsLeft() {
        return stepsLeft;     
    }
    /**
     * @return time left
     */
    public int getTimeLeft() {
        return timeLeft;
    }
    public boolean getStatus() {
        return status;
    }
}
