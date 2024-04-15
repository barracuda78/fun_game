package com.barracuda.fun.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.springframework.stereotype.Component;

@Component
public class KeyHandler implements KeyListener {

    public boolean upPressed;

    public boolean downPressed;

    public boolean leftPressed;

    public boolean rightPressed;

    public boolean paused = false;

    @Override
    public void keyTyped(KeyEvent e) {
        //we are not gonna use it
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            if (paused) {
                paused = false;
            }
            else if (!paused) {
                paused = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

}
