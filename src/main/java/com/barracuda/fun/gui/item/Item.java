package com.barracuda.fun.gui.item;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.ImageScalerServiceImpl;
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

    public final ImageScalerServiceImpl tool = new ImageScalerServiceImpl(); //TODO: IOC, it is a bean!

    public void draw(Graphics2D graphics2D, Player player) {
        int screenX = worldX - player.worldX + player.screenX;
        int screenY = worldY - player.worldY + player.screenY;

        if (
            worldX + TILE_SIZE > player.worldX - player.screenX
                &&
                worldX  - TILE_SIZE < player.worldX + player.screenX
                &&
                worldY  + TILE_SIZE > player.worldY - player.screenY
                &&
                worldY  - TILE_SIZE < player.worldY + player.screenY
        ) {
            graphics2D.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

}
