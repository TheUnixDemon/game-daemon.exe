package de.core.control;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyInput implements KeyListener {
    private boolean w, a, s, d;
    public KeyInput() {
        w = false;
        a = false;
        s = false;
        d = false;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = true;  
                System.out.println(w);              
                break;
            case KeyEvent.VK_A:
                a = true;
                System.out.println(a);
                break;
            case KeyEvent.VK_S:
                s = true;
                System.out.println(s);
                break;
            case KeyEvent.VK_D:
                System.out.println(d);
                d = true;
                break;
            default:
                break;
        }
    }   
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = false;
                break;
            case KeyEvent.VK_A:
                a = false;
                break;
            case KeyEvent.VK_S:
                s = false;
                break;
            case KeyEvent.VK_D:
                s = false;
                break;
            default:
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
