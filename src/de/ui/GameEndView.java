package de.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import de.core.control.GameController; // Import the GameController class

/**
 * Modernized UI to display the final game status and statistics.
 */
public class GameEndView extends JFrame {

    /**
     * Initializes the Game End View.
     * @param won True if the level was completed.
     * @param stepsUsed The total number of steps taken.
     * @param timeUsed The total time elapsed (in seconds).
     * @param controller The GameController instance to handle the restart action.
     */
    public GameEndView(boolean won, int stepsUsed, double timeUsed, GameController controller) {
        
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel for padding and structure
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Gap between components
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Large external padding
        
        String statusText = won ? "VICTORY!" : "DEFEAT";
        Color statusColor = won ? new Color(76, 175, 80) : new Color(244, 67, 54); // Green or Red
        
        JLabel statusLabel = new JLabel(statusText, SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 36)); // Very large and bold
        statusLabel.setForeground(statusColor);
        
        // Add the status label to the NORTH area of the BorderLayout
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        // Use GridBagLayout for flexible centering and spacing of statistics
        JPanel statsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); // Spacing around each statistics line
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // steps used
        JLabel stepsLabel = new JLabel("Steps taken: " + stepsUsed);
        stepsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 0;
        statsPanel.add(stepsLabel, gbc);
        
        // time used
        String formattedTime = String.format("%.2f", timeUsed);
        JLabel timeLabel = new JLabel("Time elapsed: " + formattedTime + " seconds");
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 1;
        statsPanel.add(timeLabel, gbc);

        // Add the statistics panel to the center area
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        
        // Panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20)); // Added horizontal gap
        
        // --- NEW RESTART BUTTON IMPLEMENTATION START ---
        JButton restartButton = new JButton("Restart Level");
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        restartButton.addActionListener(e -> {
            dispose(); // Close the Game End View
            // Call the restart method on the passed controller instance
            controller.gameRestart();
        });
        buttonPanel.add(restartButton);
        // --- NEW RESTART BUTTON IMPLEMENTATION END ---
        
        JButton closeButton = new JButton("OK / Exit Game");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        // ActionListener to close the window on click
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(closeButton);
        
        // Add the button area to the SOUTH area
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the main panel to the JFrame
        this.add(mainPanel);

        pack(); // Sizes the frame based on its components
        setMinimumSize(new Dimension(350, 200)); // Ensures a minimum size (slightly wider for two buttons)
        setLocationRelativeTo(null); // Centers the window on the screen
        setVisible(true);
    }
    
    // The previous constructor must be kept or the GameController must be updated.
    // I will add a method to create the necessary button logic and integrate it.
    
    /**
     * Initializes the Game End View (old compatibility constructor).
     * This will not support the restart functionality as it is missing the controller.
     * @param won True if the level was completed.
     * @param stepsUsed The total number of steps taken.
     * @param timeUsed The total time elapsed (in seconds).
     */
    public GameEndView(boolean won, int stepsUsed, double timeUsed) {
        // Calling the main constructor with a null controller for compatibility
        this(won, stepsUsed, timeUsed, null); 
    }
}