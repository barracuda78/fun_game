package com.barracuda.fun.gui;

import com.barracuda.fun.gui.tile.TileManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrawServiceImpl extends JPanel {

  //TODO: also create CoordinatCalculationService as a bean!
//  private final TileManager tileManager;
//
//  private final UI userMenu;
//
//  private final SoundServiceImpl soundService;
//
//  private final KeyHandler keyHandler;
//
//  private Graphics2D graphics2D;
//
//  public void paintComponent(Graphics graphics) {
//    super.paintComponent(graphics);
//    graphics2D = (Graphics2D) graphics;
//
//    //tiles
//    tileManager.draw(graphics2D, player);
//
//    //items
//    for(int i = 0; i < items.length; i++) {
//      if(items[i] != null) {
//        items[i].draw(graphics2D, this);
//      }
//    }
//
//    //npc
//    for(int i = 0; i < npcs.length; i++) {
//      if(npcs[i] != null) {
//        npcs[i].draw(graphics2D, player);
//      }
//    }
//
//    //player
//    player.draw(graphics2D);
//
//    //menu
//    userMenu.draw(graphics2D, player, gameThread);
//    if (keyHandler.paused) {
//      userMenu.drawPauseScreen();
//      soundService.stopMusic();
//    }
//    graphics2D.dispose();
//  }
//
//  public void drawTileManager() {
//
//  }

}
