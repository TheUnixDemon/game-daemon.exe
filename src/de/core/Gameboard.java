package de.core;

import java.awt.Point;

import de.core.level.Level;
import de.core.control.Direction;

/**
 * backend for gameplay
 * controls Level object such as movement & game status
 */
public class Gameboard {
    // Level data
    private Level level;
    private int stepsLeft;
    private long timeLeft;
    
    // Gameboard backend statistics
    private int stepsUsed;
    private long timeUsed;

    // status if loose or win
    private boolean status;
    
    // initalize exit of gameboard based on win conditions
    private boolean endGame;

    /**
     * sets *Left attributes
     * @param level choosen level to play
     */
    public Gameboard(Level level) {
        this.level = level;
        this.timeLeft = level.getMaxTime();
        this.stepsLeft = level.getMaxSteps();

        // sets base states for each Level
        this.status = false;
        this.endGame = false;
        this.timeUsed = 0;
        this.stepsUsed = 0;
    }

    /**
     * moves objects on gameboard map
     * -> checks if game is won
     * @param dir interpreted input signal as direction
     */
    public void moveTo(Direction dir) {
        if (dir != null) {
            Point playerTarget = dir.addTo(level.getPlayer());
            
            // exits method; no realocating
            if (level.getWalls().contains(playerTarget)) { return; }
            
            // move obstacle to new location
            if (level.getObstacles().contains(playerTarget)) {
                if (!level.getObstacles().contains(dir.addTo(playerTarget)) && !level.getWalls().contains(dir.addTo(playerTarget)) && !level.getGoals().contains(dir.addTo(playerTarget))) {
                    // move object to addTo(playerTarget)
                    int obstacleId = level.getObstacles().indexOf(playerTarget);
                    level.getObstacles().set(obstacleId, dir.addTo(playerTarget));
                } else {
                    return;
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
     * reduces stepsLeft attribute; only if set or stepsLeft = -1 (disabled)
     * initializes game to end if conditions are meet
     */
    public void decreaseStepsLeft() {
        if (stepsLeft != -1) {
            stepsLeft -= 1;
            if (stepsLeft <= 0) { this.endGame = true; }
        }
    } 

    /**
     * reduces timeLeft attribute; only if set or timeLeft = -1.0 (disabled)
     * initializes game to end if conditions are meet
     */
    public void decreaseTimeLeft(double millisecs) {
        if (timeLeft != -1) { 
            timeLeft -= millisecs;
            if (timeLeft <= 0) { this.endGame = true; }
        }
    }

    /**
     * @param timeInMillisecs time to convert in readable time to show
     * @return time in secounds with millisecounds
     */
    public double getTimeInSecs(long timeInMillisecs) {
        double timeInSecs = timeInMillisecs / 1000.0;

        int places = 3;
        double shifted = timeInSecs * Math.pow(10, places);
        long rounded = Math.round(shifted);
        return rounded / Math.pow(10, places);
    }

    /**
     * adds counter for time used for game statistics
     */
    public void addTimeUsed(double millisecs) {
        timeUsed += millisecs;
    }

    /**
     * adds counter for steps used for game statistics
     */
    public void addStepsUsed() {
        stepsUsed += 1;
    }

    /**
     * time limiter for winning condition
     * @return steps left
     */
    public int getStepsLeft() {
        return stepsLeft;
    }

    /**
     * time limiter for winning condition
     * @return time left - in secounds with millisecounds
     */
    public double getTimeLeft() {
        return getTimeInSecs(timeLeft);
    }

    /**
     * time counter for statistics
     * @return time used - in secounds with millisecounds
     */
    public double getTimeUsed() {
        return getTimeInSecs(timeUsed);
    }
    
    /**
     * step counter for statistics
     * @return steps used
     */
    public int getStepsUsed() {
        return stepsUsed;
    }

    /**
     * after game ends gameStatus will be executed
     * @return if won or lose
     */
    public boolean getStatus() {
        return status;
    }
    
    /**
     * will be executed by GameController after each action
     * @return if game should end or not (based on conditions too)
     */
    public boolean getEndGame() {
        return endGame;
    }
}
