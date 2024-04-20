package com.barracuda.fun.gui;


import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_HEIGHT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_WIDTH;

import com.barracuda.fun.gui.entity.Player;
import com.barracuda.fun.gui.tile.TileManager;
import jakarta.annotation.PostConstruct;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrawServiceImpl extends JPanel {

    private final TileManager tileManager;

    private final UI userMenu;

    private final Player player;

    private final KeyHandler keyHandler;

    private final SoundServiceImpl soundService;

    private final ItemPlacerServiceImpl itemPlacerService;

    private final NpcPlacerServiceImpl npcPlacerService;

    Graphics2D graphics2D;

    @PostConstruct
    public void init() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        itemPlacerService.setItem();
        npcPlacerService.placeNpc();
        soundService.playMusic(0);
    }

    public void paint() {
        repaint(); //this calls paintComponent(Graphics graphics) method.
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D, player.getCoordinates());
        for(int i = 0; i < itemPlacerService.getItems().length; i++) {
            if(itemPlacerService.getItems()[i] != null) {
                itemPlacerService.getItems()[i].draw(graphics2D, player);
            }
        }
        for(int i = 0; i < npcPlacerService.getNpcs().length; i++) {
            if(npcPlacerService.getNpcs()[i] != null) {
                npcPlacerService.getNpcs()[i].draw(graphics2D, player.getCoordinates());
            }
        }
        player.draw(graphics2D);
        userMenu.draw(graphics2D, player, null); //TODO fix Thread
        if (keyHandler.paused) {
            userMenu.drawPauseScreen();
            soundService.stopMusic();
        }
        graphics2D.dispose();
    }

}
