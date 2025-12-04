package de.core.control;

import java.awt.*;

/**
 * implements interpretation of input direction as coordiantes
 */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);   
    
    // current direction interpeted as Point for calculating target Point
    private final Point dir;
    Direction(int dx, int dy) { 
        dir = new Point(dx, dy);
    }
    /**
     * for checking target locations
     * @param origin base location
     * @return target coordinate based on origin
     */
    public Point addTo(Point origin) {
        return new Point(origin.x + dir.x, origin.y + dir.y);
    }
}