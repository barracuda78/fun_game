package com.barracuda.fun.gui;

import com.barracuda.fun.UI;
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
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenColumn;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS:
    public final int maxWorldColumn = 50;
    public final int maxWorldRow = 50;

    // SYSTEM:
    public final TileManager tileManager = new TileManager(this);
    private final KeyHandler keyHandler;
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public Thread gameThread;
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    ItemPlacer itemPlacer = new ItemPlacer(this);
    public UI ui = new UI(this);
    public  Graphics2D graphics2D;

    // ENTITY AND OBJECT:
    public Player player ;
    public Item[] items = new Item[99];

    // GAME STATE:
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


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
        gameState = playState;
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
        if (!keyHandler.paused) {
            player.update();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                items[i].draw(graphics2D, this);
            }
        }
        player.draw(graphics2D);
        ui.draw(graphics2D);
        if (keyHandler.paused) {
            ui.drawPauseScreen();
        }
        graphics2D.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

}
