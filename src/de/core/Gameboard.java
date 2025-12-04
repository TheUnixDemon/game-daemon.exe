package de.core;

import java.awt.*;
import de.core.level.Level;
import de.core.control.Direction;

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
    // later add parameter dir point
    public void moveTo(Direction dir) {
        switch (dir) {}
    }
    // movement of player is slightly different then moveTo
    public void movePlayer(Point target, Direction d) {}
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
