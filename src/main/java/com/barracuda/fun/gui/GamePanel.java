package com.barracuda.fun.gui;

import com.barracuda.fun.gui.entity.Player;
import com.barracuda.fun.gui.tile.TileManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

@Component
public class GamePanel extends JPanel implements Runnable {

    public static final int FPS = 60;

    final int originalTileSize = 16; //TODO: move to properties file

    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    final int maxScreenColumn = 16;

    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenColumn;

    final int screenHeight = tileSize * maxScreenRow;

    private final TileManager tileManager = new TileManager(this);
    private final KeyHandler keyHandler;

    Thread gameThread;

    private Player player ;

//    int playerX = 100;
//
//    int playerY = 100;
//
//    int playerSpeed = 4;

    public GamePanel(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        player = new Player(this, keyHandler);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000/ FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update(player);
                repaint(); //here we call paintComponent(Graphics graphics) method
                delta--;
            }

        }
    }

    public void update(Player player) {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
        graphics2D.dispose();
    }
}
