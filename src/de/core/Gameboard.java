package de.core;

import java.awt.*;
import de.core.level.Level;

/** 
 * backend movement and repositioning of player & tiles
 * 
 * //needs check // bitte gameboard extends(nicht extends weil render diese auslesen sollte) level weil dann läuft mapping und relocating ohne reduncanz und in geil lan 
 * //removing parts inside of Level //
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
    /**
     * 
     * @param target new object position
     * @param d
     */ //needs a complete rework //
    // no boolean required; every move will be renderd (the render class reads the map)
    public void moveTo(Point target, Direction d) {
        // foreach is not needed for ArrayList<Point>; equals is correct implemented
        for (Point o : level.getObstacles()) {
            // 
            if (target.equals(o)) {
                
            }
        } 
        for (Point g : l.getGoals()) {
            
        }
        if (level.getGoals().equals(target)) {

        }
    }
    // movement of player is slightly different then moveTo
    public void movePlayer(Point target, Direction d)
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
