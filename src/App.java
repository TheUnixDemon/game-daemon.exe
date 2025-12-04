import javax.swing.*;

import de.core.control.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        KeyInput keyInput = new KeyInput();

        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(keyInput);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
    }
}