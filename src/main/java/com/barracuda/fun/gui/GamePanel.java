package com.barracuda.fun.gui;

import com.barracuda.fun.gui.entity.Player;
import com.barracuda.fun.gui.item.Item;
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


    //SCREEN SETTINGS:
    public static final int FPS = 60;

    public final int originalTileSize = 16; //TODO: move to properties file

    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenColumn = 16;

    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenColumn;

    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS:
    public final int maxWorldColumn = 50;

    public final int maxWorldRow = 50;

//    public final int worldWidth = tileSize * maxWorldColumn;
//
//    public final int worldHeight = tileSize * maxWorldRow;

    // SYSTEM:
    public final TileManager tileManager = new TileManager(this);

    private final KeyHandler keyHandler;

    Sound sound = new Sound();

    Thread gameThread;

    public final CollisionChecker collisionChecker = new CollisionChecker(this);

    ItemPlacer itemPlacer = new ItemPlacer(this);

    // ENTITY AND OBJECT:
    public Player player ;

    public Item[] items = new Item[10];

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

    public void setupGame() {
        itemPlacer.setItem();
        playMusic(0);
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
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                items[i].draw(graphics2D, this);
            }
        }
        player.draw(graphics2D);
        graphics2D.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();

    }

}
