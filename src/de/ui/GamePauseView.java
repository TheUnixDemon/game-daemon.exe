package de.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*; // needed for KeyEvent and ActionListener
import de.core.control.GameController;

/**
 * Pause menu that can be toggled with ESC.
 * Contains the same buttons as GameEndView (Restart, Back to Menu, Exit) and a Resume button.
 */
public class GamePauseView extends JFrame {

    public GamePauseView(GameController controller, JFrame frame) {
        setTitle("Pause");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true); // Optional: für modernes Overlay
        setAlwaysOnTop(true); // Damit es über dem Spiel bleibt

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Status label
        JLabel pauseLabel = new JLabel("PAUSE", SwingConstants.CENTER);
        pauseLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        pauseLabel.setForeground(new Color(33, 150, 243)); // Blau
        mainPanel.add(pauseLabel, BorderLayout.NORTH);

        // Button panel (center)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(220, 40);

        // Resume button
        JButton resumeButton = new JButton("Weiter");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setMaximumSize(buttonSize);
        resumeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        resumeButton.addActionListener(e -> {
            // Ask controller to resume (controller will handle timer & closing)
            controller.resumeFromPause();
        });
        buttonPanel.add(resumeButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        // Restart button
        JButton restartButton = new JButton("Level neustarten");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(buttonSize);
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        restartButton.addActionListener(e -> {
            dispose();
            controller.gameRestart();
        });
        buttonPanel.add(restartButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        // Back to menu button
        JButton backToMenuButton = new JButton("Level Menü");
        backToMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMenuButton.setMaximumSize(buttonSize);
        backToMenuButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backToMenuButton.addActionListener(e -> {
            dispose();
            frame.dispose();
            controller.stopBackgroundMusic();
            controller.setBackToMenu(true);
        });
        buttonPanel.add(backToMenuButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        // Exit button
        JButton exitButton = new JButton("Spiel verlassen");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(buttonSize);
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.addActionListener(e -> {
            dispose();
            frame.dispose();
            controller.setGameStatus(false);
        });
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Register ESC key on this window to resume — works even if pause window has focus.
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "resume");
        am.put("resume", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resumeFromPause();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
