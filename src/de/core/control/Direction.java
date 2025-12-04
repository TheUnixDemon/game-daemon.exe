package de.core.control;

import java.awt.*;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);   
    
    private final Point point;
    Direction(int dx, int dy) { 
        point = new Point(dx, dy);
    }
    public Point getPoint() {
        return point;
    }
}