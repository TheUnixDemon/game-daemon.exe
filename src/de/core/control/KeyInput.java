package de.core.control;

import java.awt.event.*;

public class KeyInput implements KeyListener {
    private Direction dir;
    public KeyInput() {
        dir = null;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                dir = Direction.UP;
                break;
            case KeyEvent.VK_A:
                dir = Direction.LEFT;
                break;
            case KeyEvent.VK_S:
                dir = Direction.DOWN;
                break;
            case KeyEvent.VK_D:
                dir = Direction.RIGHT;
                break;
            default:
                dir = null;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
            dir = null;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
