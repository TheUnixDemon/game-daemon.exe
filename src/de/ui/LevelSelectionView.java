package de.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import de.core.control.GameController;
import de.core.control.GameStatus;
import de.core.level.Level;

/**
 * Enhanced UI for level selection. Uses BorderLayout and fixed size for better aesthetics.
 */
public class LevelSelectionView extends JFrame implements ActionListener {
    private ArrayList<Level> levels;
    private JComboBox<String> levelDropdown;
    private JButton startButton;
    private JButton closeButton;

    // game controller for creating gameview and gameboard
    private GameController gameController;
    
    // game status save for returning or exiting game
    private GameStatus gameStatus;

    // Fixed preferred window size
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;

    /**
     * Initializes the level selection view.
     * @param levels The list of Level objects available for selection.
     */
    public LevelSelectionView(ArrayList<Level> levels, GameStatus gameStatus) {
        this.levels = levels;
        this.gameStatus = gameStatus;
        
        // 1. Basic window setup
        setTitle("Gamon Level-Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set fixed preferred size
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        // Use BorderLayout for main structure
        setLayout(new BorderLayout(10, 10)); // 10px padding between regions
        
        // 2. Header / Title Label
        JLabel titleLabel = new JLabel("Wählen Sie Ihr Gamon-Level:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
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
        levelDropdown.setFont(new Font("Arial", Font.PLAIN, 18));
        levelDropdown.setSelectedIndex(0);
        
        JLabel levelSelection = new JLabel("Level-Auswahl: ");
        levelSelection.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(levelSelection);
        centerPanel.add(levelDropdown);
        add(centerPanel, BorderLayout.CENTER);

        // 4. South Panel for Button
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0)); // Bottom padding

        Dimension buttonSize = new Dimension(WINDOW_WIDTH / 2, 60);

        startButton = new JButton("Spiel starten");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        startButton.addActionListener(this);

        closeButton = new JButton("Spiel verlassen");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setPreferredSize(buttonSize);
        closeButton.setMaximumSize(buttonSize);
        closeButton.addActionListener(e -> {
            dispose();
            gameStatus.setGameStatus(false);
        });
     
        southPanel.add(startButton);
        southPanel.add(closeButton);

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
                gameController = new GameController(selectedLevel, gameStatus);
            } else {
                // Error message in German
                JOptionPane.showMessageDialog(this, 
                    "Bitte wählen Sie ein gültiges Level.", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * calls isAlive() in gameController for exiting in Gamecore if game is dead (-> to games menu)
     * @return game status for exiting
     */
    public boolean getGameStatus() {
        return gameStatus.getGameStatus();
    }

    public boolean getBackToMenu() {
        return gameStatus.getBackToMenu();
    }
}