package de.core.control;

import java.awt.*;

/**
 * implements interpretation of input direction as coordiantes
 */
public enum Direction {
    // inverted coordinates for right directions on gameView
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);   
    
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