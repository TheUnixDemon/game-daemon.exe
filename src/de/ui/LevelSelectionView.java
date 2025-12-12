package de.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import de.core.control.GameController;
import de.core.level.Level;

/**
 * Enhanced UI for level selection. Uses BorderLayout and fixed size for better aesthetics.
 */
public class LevelSelectionView extends JFrame implements ActionListener {

    private ArrayList<Level> levels;
    private JComboBox<String> levelDropdown;
    private JButton startButton;

    // Fixed preferred window size
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 200;

    /**
     * Initializes the level selection view.
     * @param levels The list of Level objects available for selection.
     */
    public LevelSelectionView(ArrayList<Level> levels) {
        this.levels = levels;
        
        // 1. Basic window setup
        setTitle("Goman Level-Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set fixed preferred size
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        // Use BorderLayout for main structure
        setLayout(new BorderLayout(10, 10)); // 10px padding between regions
        
        // 2. Header / Title Label
        JLabel titleLabel = new JLabel("Wählen Sie Ihr Goman-Level:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Top padding
        add(titleLabel, BorderLayout.NORTH);

        // 3. Center Panel for Dropdown and Label
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        // Create Dropdown data
        String[] levelNames = new String[levels.size()];
        for (int i = 0; i < levels.size(); i++) {
            levelNames[i] = "Level " + (i + 1); 
        }
        levelDropdown = new JComboBox<>(levelNames);
        levelDropdown.setSelectedIndex(0);
        
        centerPanel.add(new JLabel("Level:"));
        centerPanel.add(levelDropdown);
        add(centerPanel, BorderLayout.CENTER);

        // 4. South Panel for Button
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Bottom padding

        // Create Start Button
        startButton = new JButton("Spiel starten");
        startButton.addActionListener(this); 
        startButton.setPreferredSize(new Dimension(WINDOW_WIDTH / 2, 40)); // Make button wider
        
        southPanel.add(startButton);
        add(southPanel, BorderLayout.SOUTH);

        // Display window
        pack(); // Packs to the preferred size set above
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    /**
     * Handles the start button click event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            
            int selectedIndex = levelDropdown.getSelectedIndex();
            
            if (selectedIndex >= 0 && selectedIndex < levels.size()) {
                
                Level selectedLevel = levels.get(selectedIndex);
                
                // Close selection window
                this.dispose(); 
                
                // Start the game
                new GameController(selectedLevel);
                
            } else {
                // Error message in German
                JOptionPane.showMessageDialog(this, 
                    "Bitte wählen Sie ein gültiges Level.", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}