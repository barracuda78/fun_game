package com.barracuda.fun.gui.item;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.UtilityTool;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import jdk.jshell.execution.Util;

public class Item {

    public BufferedImage image;

    public String name;

    public boolean supportsCollision = false;

    public int worldX;

    public int worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;

    public int solidAreaDefaultY = 0;

    public UtilityTool tool = new UtilityTool();

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
            worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                &&
                worldX  - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                &&
                worldY  + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                &&
                worldY  - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
