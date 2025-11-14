package de.core;

import de.core.level.Level;

/** bitte gameboard extends level weil dann läuft mapping und relocating ohne reduncanz und in geil lan */
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
