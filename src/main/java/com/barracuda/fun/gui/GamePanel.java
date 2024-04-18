package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.FPS;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_HEIGHT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_WIDTH;

import com.barracuda.fun.gui.entity.Entity;
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

    // SYSTEM:
    private final TileManager tileManager;
    private final KeyHandler keyHandler;
    private final CollisionChecker collisionChecker;
    private final ItemPlacerServiceImpl itemPlacerService;
    private final NpcPlacerServiceImpl npcPlacerService;
    private final SoundServiceImpl soundService;
    private final UI userMenu;


    public Thread gameThread;
    public Graphics2D graphics2D;

    // ENTITY AND OBJECT:
    public Player player ;
    public Item[] items = new Item[99];
    public Entity[] npcs = new Entity[10];

    // GAME STATE:
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;


    public GamePanel(KeyHandler keyHandler, ItemPlacerServiceImpl itemPlacerService, NpcPlacerServiceImpl npcPlacerService, TileManager tileManager, CollisionChecker collisionChecker, SoundServiceImpl soundService, UI userMenu) {
        this.itemPlacerService = itemPlacerService;
        this.npcPlacerService =  npcPlacerService;
        this.tileManager = tileManager;
        this.keyHandler = keyHandler;
        this.collisionChecker = collisionChecker;
        this.soundService = soundService;
        this.userMenu = userMenu;
        player = new Player(this.collisionChecker, this.keyHandler, this.soundService, userMenu);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        itemPlacerService.setItem(this);
        npcPlacerService.setNpc(collisionChecker, npcs);
        soundService.playMusic(0);
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
            player.update(items, npcs);
            for (int i = 0; i < npcs.length; i++) {
                if(npcs[i] != null) {
                    npcs[i].update(player, items);
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D, player);
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                items[i].draw(graphics2D, this);
            }
        }
        for(int i = 0; i < npcs.length; i++) {
            if(npcs[i] != null) {
                npcs[i].draw(graphics2D, player);
            }
        }
        player.draw(graphics2D);
        userMenu.draw(graphics2D, player, gameThread);
        if (keyHandler.paused) {
            userMenu.drawPauseScreen();
            soundService.stopMusic();
        }
        graphics2D.dispose();
    }



}
