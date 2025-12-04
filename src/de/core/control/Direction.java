package de.core.control;

import java.awt.*;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);   
    
    public final Point dir;
    Direction(int dx, int dy) { 
        dir = new Point(dx, dy);
    }
}