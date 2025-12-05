package de.core.control;

import de.core.level.Level;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

/**
 * GameView ist die visuelle Komponente (ersetzt das alte GameBoard),
 * die den Zustand des Level-Objekts zeichnet.
 */
public class GameView extends JPanel {
    private Level level;
    private final int TILE_SIZE = 40; 
    
    // Farbdefinitionen
    private final Color WALL_COLOR = new Color(50, 50, 50);
    private final Color FLOOR_COLOR = new Color(200, 200, 200);
    private final Color PLAYER_COLOR = Color.BLUE;
    private final Color OBSTACLE_COLOR = new Color(139, 69, 19); 
    private final Color GOAL_COLOR = new Color(0, 150, 0); 


    public GameView(Level level) {
        this.level = level;
        // Berechnung der Größe des Panels basierend auf der Level-Größe
        int width = level.getMap().get(0).length * TILE_SIZE;
        int height = level.getMap().size() * TILE_SIZE;
        setPreferredSize(new Dimension(width, height));
        setBackground(FLOOR_COLOR);
    }

    /**
     * Zeichnet die Map-Elemente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Hintergrund und Wände zeichnen
        for (int row = 0; row < level.getMap().size(); row++) {
            for (int col = 0; col < level.getMap().get(row).length; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;
                
                if (level.getWalls().contains(new Point(row, col))) {
                    g2d.setColor(WALL_COLOR);
                    g2d.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                } else {
                    g2d.setColor(FLOOR_COLOR);
                    g2d.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        
        // 2. Ziele zeichnen
        for (Point goal : level.getGoals()) {
            g2d.setColor(GOAL_COLOR);
            g2d.drawRect(goal.y * TILE_SIZE, goal.x * TILE_SIZE, TILE_SIZE, TILE_SIZE); 
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(goal.y * TILE_SIZE + 5, goal.x * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
            g2d.setStroke(new BasicStroke(1));
        }

        // 3. Hindernisse (Kisten) zeichnen
        for (Point obstacle : level.getObstacles()) {
            g2d.setColor(OBSTACLE_COLOR);
            g2d.fillRect(obstacle.y * TILE_SIZE + 5, obstacle.x * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(obstacle.y * TILE_SIZE + 5, obstacle.x * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
        }

        // 4. Spieler zeichnen
        Point player = level.getPlayer();
        g2d.setColor(PLAYER_COLOR);
        g2d.fillOval(player.y * TILE_SIZE + 5, player.x * TILE_SIZE + 5, TILE_SIZE - 10, TILE_SIZE - 10);
    }
}