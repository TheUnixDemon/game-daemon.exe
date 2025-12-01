package de.core;

import java.awt.*;
import de.core.level.Level;

/** 
 * backend movement and repositioning of player & tiles
 * 
 * //needs check // bitte gameboard extends level weil dann läuft mapping und relocating ohne reduncanz und in geil lan 
*/
public class Gameboard {
    private Level level;
    private int stepsLeft;
    private int timeLeft;
    private boolean status;
    public Gameboard(Level level) {
        this.stepsLeft = level.getMaxSteps();
        this.timeLeft = level.getMaxTime();
        this.status = false;
    }
    public void moveTo(Point target, Direction d) {
        for (Point o : level.getObstacles()) {
            if (target) {

            }
        } 
        for (Point g : l.getGoals()) {
            
        }
        if (level.getGoals().equals(target)) {

        }
    }
    // reducing timeLeft status
    public void tickSecound() {
        timeLeft -= 1;
    }
    public int getStepsLeft() {
        return stepsLeft;     
    }
    public int getTimeLeft() {
        return timeLeft;
    }
}
