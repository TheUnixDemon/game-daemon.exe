package de.core.control;

import java.awt.event.*;

/**
 * interprets key input & implements Direction
 */
public class KeyInput implements KeyListener {
    private Direction dir; 
    private Direction nextDir;
    private boolean isPaused;
    private boolean easteregg;

    /**
     * sets current pressed directions to base state
     */
    public KeyInput() {
        dir = null;
        nextDir = null;
        isPaused = false;
        easteregg = false;
    }

    /**
     * checks key input using KeyEvent; interprets input using Direction
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePause();
            dir = null;
        }
        if (isPaused) { return; }
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
            case KeyEvent.VK_C:
                easteregg = !easteregg;
                break;
            default:
                dir = null;
                break;
        }

        if (dir != null && nextDir == null) {
            nextDir = dir;
        }
    }

    /**
     * sets dir to null after * key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        dir = null;
    }

    /**
     * implementation nessessary by KeyListener interface 
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * for relocating of player
     * @return direction of movement
     */
    public Direction getDirection() {
        Direction currentDir = nextDir;
        nextDir = null;
        return currentDir;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }
    public boolean getIsPaused() {
        return isPaused;
    }

    public boolean getEastereggStatus() {
        return easteregg;
    }
}
