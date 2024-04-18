package com.barracuda.fun.gui.entity;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.ImageScalerServiceImpl;
import com.barracuda.fun.gui.item.Item;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {

    final CollisionChecker collisionChecker;

    public int worldX;

    public int worldY;

    public int speed;

    public BufferedImage up_1;
    public BufferedImage up_2;
    public BufferedImage down_1;
    public BufferedImage down_2;
    public BufferedImage left_1;
    public BufferedImage left_2;
    public BufferedImage right_1;
    public BufferedImage right_2;

    public String direction;

    public int spriteCounter = 0;

    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public int solidAreaDefaultX;

    public int solidAreaDefaultY;

    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public Entity(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public void setAction() {

    }

    public void update(Player player, Item[] items) {
        setAction();
        collisionOn = false;
        collisionChecker.checkTile(this);
        collisionChecker.checkItem(this, false, items);
        collisionChecker.checkPlayer(this, player);
        //todo: copied from player class! refactor
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D, Player player) {
        BufferedImage image = null;
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
            switch (direction) {
                case "up":
                    if (spriteNumber == 1) {
                        image = up_1;
                    }
                    if (spriteNumber == 2) {
                        image = up_2;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        image = down_1;
                    }
                    if (spriteNumber == 2) {
                        image = down_2;
                    }
                    break;
                case "left":
                    if (spriteNumber == 1) {
                        image = left_1;
                    }
                    if (spriteNumber == 2) {
                        image = left_2;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        image = right_1;
                    }
                    if (spriteNumber == 2) {
                        image = right_2;
                    }
                    break;
            }

            graphics2D.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public BufferedImage setup(String imagePath) {
        ImageScalerServiceImpl tool = new ImageScalerServiceImpl();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream( imagePath));
            scaledImage = tool.scaleImage(scaledImage, TILE_SIZE, TILE_SIZE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}
