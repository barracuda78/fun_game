package com.barracuda.fun.gui.item;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.entity.Player;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Item {

    public BufferedImage image;

    public String name;

    public boolean supportsCollision = false;

    public int worldX;

    public int worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX = 0;

    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D graphics2D, Player player) {
        int screenX = worldX - player.worldX + SCREEN_CENTER_X;
        int screenY = worldY - player.worldY + SCREEN_CENTER_Y;

        if (
            worldX + TILE_SIZE > player.worldX - SCREEN_CENTER_X
                &&
                worldX  - TILE_SIZE < player.worldX + SCREEN_CENTER_X
                &&
                worldY  + TILE_SIZE > player.worldY - SCREEN_CENTER_Y
                &&
                worldY  - TILE_SIZE < player.worldY + SCREEN_CENTER_Y
        ) {
            graphics2D.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

}
