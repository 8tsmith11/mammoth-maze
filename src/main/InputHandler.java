package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private boolean[] keys = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 256) keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 256) keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    public boolean isKeyDown(int keyCode) {
        if (keyCode < 256) return keys[keyCode];
        return false;

    }

}
