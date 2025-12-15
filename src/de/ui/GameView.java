package de.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import de.core.level.Level;

/**
 * ui component of Gameboard; draws Gamboard and game statistics.
 * draws current position of Level objects on window screen
 */
public class GameView extends JPanel {
    private Level level; // current choosen level
    private boolean eastereggStatus;

    // variable tile size
    private int tileSize = 0;
    private final int MIN_TILE_SIZE = 35; // smallest tile size
    private final int MAX_TILE_SIZE = 70; // biggest tile size

    // image caching
    private Map<String, Image> imageCache = new HashMap<>();
    private String basePath = "./srv/tile/"; // CHANGEME
    private String wallImg = basePath + "wall.jpg";
    private String wallEggImg = basePath + "wall-egg.jpg";
    private String playerImg = basePath + "player.png";
    private String playerEggImg = basePath + "player-egg.png";
    private String goalImg = basePath + "goal.png";
    private String obstacleImg = basePath + "obstacle.png";

    // centralized gameboard
    private int offsetX = 0;
    private int offsetY = 0;

    // Data received from GameController
    private int stepsLeft = -1; // Default: No step limit
    private double timeLeft = -1.0; // Default: No time limit

    // colors for testing & fillin if tile set not found (img not found)
    private final Color FLOOR_COLOR = new Color(30,30,30);
    private final Color PLAYER_COLOR = Color.BLUE;
    private final Color GOAL_COLOR = new Color(0, 150, 0); 
    private final Color STATS_COLOR = Color.WHITE; // New color for stats

    public GameView(Level level) {
        this.level = level;
        eastereggStatus = false;

        setBackground(FLOOR_COLOR);
        setFocusable(true);

        // base size for window
        int padding = 300;
        int width = level.getMap().get(0).length * MIN_TILE_SIZE + padding;
        int height = level.getMap().size() * MIN_TILE_SIZE + padding;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Public method to receive updated statistics from GameController.
     * @param stepsLeft current steps left (-1 if unlimited)
     * @param timeLeft current time left in seconds (-1.0 if unlimited)
     */
    public void updateStats(int stepsLeft, double timeLeft) {
        this.stepsLeft = stepsLeft;
        this.timeLeft = timeLeft;
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

        // Draw background (optional: only needed if map drawing doesn't fill the space)
        g2d.setColor(new Color(139,125,123));
        g2d.fillRect(offsetX, offsetY, cols * tileSize, rows * tileSize);

        // Draw goals
        drawGoals(g2d);

        // Draw Gameboard (walls and floor)
        if (eastereggStatus) { 
            drawEggMap(g2d, rows, cols);
            drawEggPlayer(g2d);
        } else {
            drawMap(g2d, rows, cols);
            drawPlayer(g2d);
        }

        // Draw obstacles (boxes)
        drawObstacles(g2d);

        // Draw statistics (new feature)
        drawStats(g2d);
    }
    
    /**
     * Draws the floor and walls of the map.
     */
    private void drawMap(Graphics2D g2d, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                Point tile = new Point(row, col);
                if (level.getWalls().contains(new Point(row, col))) {
                    drawImageTile(g2d, wallImg, tile);
                } else {
                    g2d.setColor(FLOOR_COLOR);
                    g2d.fillRect(tile.x, tile.y, tileSize, tileSize);
                }
            }
        }
    }

    /**
     * Draws the floor and walls of the map.
     */
    private void drawEggMap(Graphics2D g2d, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                Point tile = new Point(row, col);
                if (level.getWalls().contains(new Point(row, col))) {
                    drawImageTile(g2d, wallEggImg, tile);
                } else {
                    g2d.setColor(FLOOR_COLOR);
                    g2d.fillRect(tile.x, tile.y, tileSize, tileSize);
                }
            }
        }
    }

    /**
     * Draws the goals
     */
    private void drawGoals(Graphics2D g2d) {
        g2d.setColor(GOAL_COLOR);
        g2d.setStroke(new BasicStroke(Math.max(1, tileSize/10)));
        for (Point goal : level.getGoals()) {
            drawImageTile(g2d, goalImg, goal);
        }
    }
    
    /**
     * Draws the obstacles (boxes).
     */
    private void drawObstacles(Graphics2D g2d) {
        for (Point obstacle : level.getObstacles()) {
            drawImageTile(g2d, obstacleImg, obstacle);
        }
    }
    
    /**
     * Draws the player.
     */
    private void drawPlayer(Graphics2D g2d) {
        Point player = level.getPlayer();
        drawImageTile(g2d, playerImg, player);
    }

    /**
     * Draws the player. easteregg version
     */
    private void drawEggPlayer(Graphics2D g2d) {
        Point player = level.getPlayer();
        drawImageTile(g2d, playerEggImg, player);
    }

    /**
     * Draws the steps left and time left at the bottom left of the panel.
     */
    private void drawStats(Graphics2D g2d) {
        int xPos = 10;
        int yPos = getHeight() - 10;
        int lineHeight = 20;

        g2d.setColor(STATS_COLOR);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Format the time to 2 decimal places for better readability
        String formattedTime = String.format("%.2f", timeLeft);

        // Draw Time Left if a limit is set (not -1.0)
        if (timeLeft != -1L) {
            String timeText = "        Zeit übrig: " + formattedTime + " s";
            g2d.drawString(timeText, xPos, yPos);
            yPos -= lineHeight; // Move up for the next stat
        }

        // Draw Steps Left if a limit is set (not -1)
        if (stepsLeft != -1) {
            String stepsText = "Schritte übrig: " + stepsLeft;
            g2d.drawString(stepsText, xPos, yPos);
        }
    }

    /**
     * rescales tile size for window; based on max & min size of tile
     */
    private void calculateScaling() {
        int mapWidth = level.getMap().get(0).length;
        int mapHeight = level.getMap().size();

        int tileW = Math.min(Math.max(getWidth() / mapWidth, MIN_TILE_SIZE), MAX_TILE_SIZE);
        int tileH = Math.min(Math.max(getHeight() / mapHeight, MIN_TILE_SIZE), MAX_TILE_SIZE);
        
        tileSize = Math.min(tileW, tileH);

        offsetX = (getWidth() - (mapWidth * tileSize)) / 2;
        offsetY = (getHeight() - (mapHeight * tileSize)) / 2;
    }

    /**
     * Draw an image that is placed on a tile (tilePosition: Point(row, col)).
     * If image missing, draw a fallback (oval).
     */
    private void drawImageTile(Graphics2D g2d, String imagePath, Point tilePosition) {
        int px = offsetX + tilePosition.y * tileSize + 2; // pixel x
        int py = offsetY + tilePosition.x * tileSize + 2; // pixel y
        int size = Math.max(1, tileSize - 4);

        Image img = loadImage(imagePath);
        if (img != null) {
            g2d.drawImage(img, px, py, size, size, this);
        } else {
            // fallback (simple shape so missing image doesn't break layout)
            g2d.setColor(PLAYER_COLOR);
            g2d.fillOval(px, py, size, size);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(px, py, size, size);
        }
    }

    /**
     * Load image once and cache it.
     */
    private Image loadImage(String path) {
        if (imageCache.containsKey(path)) return imageCache.get(path);
        try {
            Image img = ImageIO.read(new File(path));
            imageCache.put(path, img);
            return img;
        } catch (IOException e) {
            // cache null to avoid repeated attempts
            imageCache.put(path, null);
            System.err.println("Bild nicht gefunden: " + path + " -> " + e.getMessage());
            return null;
        }
    }

    public void setEastereggStatus(boolean eastereggStatus) {
        this.eastereggStatus = eastereggStatus;
    }
}