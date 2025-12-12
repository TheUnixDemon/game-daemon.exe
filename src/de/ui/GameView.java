package de.ui;

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import de.core.level.Level;

/**
 * ui component of Gameboard; draws Gamboard
 * draws current position of Level objects on window screen
 */
public class GameView extends JPanel {
    private Level level; // current choosen level

    // variable tile size
    private int tileSize = 0;
    private final int MIN_TILE_SIZE = 35; // smallest tile size
    private final int MAX_TILE_SIZE = 60; // biggest tile size

    // centralized gameboard
    private int offsetX = 0;
    private int offsetY = 0;

    // colors for testing & fillin if tile set not found (img not found)
    private final Color WALL_COLOR = new Color(50, 50, 50);
    private final Color FLOOR_COLOR = new Color(200, 200, 200);
    private final Color PLAYER_COLOR = Color.BLUE;
    private final Color OBSTACLE_COLOR = new Color(139, 69, 19); 
    private final Color GOAL_COLOR = new Color(0, 150, 0); 

    public GameView(Level level) {
        this.level = level;

        setBackground(FLOOR_COLOR);
        setFocusable(true);

        // base size for window
        int width = level.getMap().get(0).length * MIN_TILE_SIZE;
        int height = level.getMap().size() * MIN_TILE_SIZE;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * draws & rescales tiles on gameboard window
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // rescale tiles based on screen size
        calculateScaling();

        int rows = level.getMap().size();
        int cols = level.getMap().get(0).length;

        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRect(offsetX, offsetY, cols * tileSize, rows * tileSize);

        // draw walls & gameboard
        for (int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                int x = offsetX + col * tileSize;
                int y = offsetY + row * tileSize;
            
                if (level.getWalls().contains(new Point(row, col))) {
                    g2d.setColor(WALL_COLOR);
                    g2d.fillRect(x, y, tileSize, tileSize);
                } else {
                    g2d.setColor(FLOOR_COLOR);
                    g2d.fillRect(x, y, tileSize, tileSize);
                }
            }
        }

        // draw goals
        g2d.setColor(GOAL_COLOR);
        g2d.setStroke(new BasicStroke(Math.max(1, tileSize/10)));
        for (Point goal : level.getGoals()) {
            g2d.drawRect(offsetX + goal.y * tileSize + 5, offsetY + goal.x * tileSize + 5, tileSize - 10, tileSize - 10);
        }

        // draw obstacles (boxes)
        for (Point obstacle : level.getObstacles()) {
            g2d.setColor(OBSTACLE_COLOR);
            g2d.fillRect(offsetX + obstacle.y * tileSize + 2, offsetY + obstacle.x * tileSize + 2, tileSize - 4, tileSize - 4);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(offsetX + obstacle.y * tileSize + 2, offsetY + obstacle.x * tileSize + 2, tileSize - 4, tileSize - 4);
        }

        // draw player
        Point player = level.getPlayer();
        g2d.setColor(PLAYER_COLOR);
        g2d.fillOval(offsetX + player.y * tileSize + 2, offsetY + player.x * tileSize + 2, tileSize - 4, tileSize - 4);
    }

    /**
     * rescales tile size for window; based on max & min size of tile
     */
    private void calculateScaling() {
        int mapWidth = level.getMap().get(0).length;
        int mapHeight = level.getMap().size();

        // Berechne wie viel Platz ein Tile maximal haben darf
        int tileW = Math.min(Math.max(getWidth() / mapWidth, MIN_TILE_SIZE), MAX_TILE_SIZE);
        int tileH = Math.min(Math.max(getHeight() / mapHeight, MIN_TILE_SIZE), MAX_TILE_SIZE);
        
        tileSize = Math.min(tileW, tileH); // Wähle den kleineren Wert für Proportionalität

        // Berechne Offsets für die Zentrierung innerhalb des Panels
        offsetX = (getWidth() - (mapWidth * tileSize)) / 2;
        offsetY = (getHeight() - (mapHeight * tileSize)) / 2;
    }
}