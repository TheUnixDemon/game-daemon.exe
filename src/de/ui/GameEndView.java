package de.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modernized UI to display the final game status and statistics.
 */
public class GameEndView extends JFrame {

    /**
     * Initializes the Game End View.
     * @param won True if the level was completed.
     * @param stepsUsed The total number of steps taken.
     * @param timeUsed The total time elapsed (in seconds).
     */
    public GameEndView(boolean won, int stepsUsed, double timeUsed) {
        
        // --- 1. Basic Window Setup ---
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // --- 2. Main Container & Layout ---
        // Main panel for padding and structure
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Gap between components
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Large external padding
        
        // --- 3. Status Label (Top) ---
        String statusText = won ? "VICTORY!" : "DEFEAT";
        Color statusColor = won ? new Color(76, 175, 80) : new Color(244, 67, 54); // Green or Red
        
        JLabel statusLabel = new JLabel(statusText, SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 36)); // Very large and bold
        statusLabel.setForeground(statusColor);
        
        // Add the status label to the NORTH area of the BorderLayout
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        // --- 4. Statistics Panel (Center) ---
        // Use GridBagLayout for flexible centering and spacing of statistics
        JPanel statsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); // Spacing around each statistics line
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // Steps
        JLabel stepsLabel = new JLabel("Steps taken: " + stepsUsed);
        stepsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 0;
        statsPanel.add(stepsLabel, gbc);
        
        // Time
        String formattedTime = String.format("%.2f", timeUsed);
        JLabel timeLabel = new JLabel("Time elapsed: " + formattedTime + " seconds");
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 1;
        statsPanel.add(timeLabel, gbc);

        // Add the statistics panel to the CENTER area
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        
        // --- 5. Button (Bottom) ---
        JButton closeButton = new JButton("OK / Exit Game");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        // ActionListener to close the window on click
        closeButton.addActionListener(e -> dispose());
        
        // Panel for the button to center it and provide spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20)); 
        buttonPanel.add(closeButton);
        
        // Add the button area to the SOUTH area
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the main panel to the JFrame
        this.add(mainPanel);

        // --- 6. Finalization ---
        pack(); // Sizes the frame based on its components
        setMinimumSize(new Dimension(300, 200)); // Ensures a minimum size
        setLocationRelativeTo(null); // Centers the window on the screen
        setVisible(true);
    }
}