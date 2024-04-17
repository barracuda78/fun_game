package com.barracuda.fun.gui.item;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.ImageScalerServiceImpl;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import lombok.RequiredArgsConstructor;

public class Item {

    public BufferedImage image;

    public String name;

    public boolean supportsCollision = false;

    public int worldX;

    public int worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;

    public int solidAreaDefaultY = 0;

    public final ImageScalerServiceImpl tool = new ImageScalerServiceImpl();

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
            worldX + TILE_SIZE > gamePanel.player.worldX - gamePanel.player.screenX
                &&
                worldX  - TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX
                &&
                worldY  + TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY
                &&
                worldY  - TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

}
