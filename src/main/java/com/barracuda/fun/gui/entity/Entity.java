package com.barracuda.fun.gui.entity;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.ImageScalerServiceImpl;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandler;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import com.barracuda.fun.gui.item.Item;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Entity {

    private final DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry;
    final CollisionChecker collisionChecker;

//    public int worldX;
//
//    public int worldY;

    private final Point coordinates;

    public int speed;

    public BufferedImage up_1; //TODO: avoid using them. use list instead
    public BufferedImage up_2;
    public BufferedImage down_1;
    public BufferedImage down_2;
    public BufferedImage left_1;
    public BufferedImage left_2;
    public BufferedImage right_1;
    public BufferedImage right_2;

    public List<BufferedImage> images = new ArrayList<>();


    public String direction;

    public int spriteCounter = 0;

    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public int solidAreaDefaultX;

    public int solidAreaDefaultY;

    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public Entity(CollisionChecker collisionChecker, DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry) {
        this.collisionChecker = collisionChecker;
        this.drawEntityDirectionHandlerRegistry = drawEntityDirectionHandlerRegistry;
        this.coordinates = new Point();
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
                    coordinates.y -= speed;
                    break;
                case "down":
                    coordinates.y += speed;
                    break;
                case "left":
                    coordinates.x -= speed;
                    break;
                case "right":
                    coordinates.x += speed;
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

    public void draw(Graphics2D graphics2D, Point playerCoordinates) {
        int screenX = coordinates.x - playerCoordinates.x + SCREEN_CENTER_X;
        int screenY = coordinates.y - playerCoordinates.y + SCREEN_CENTER_Y;

        if (
            coordinates.x + TILE_SIZE > playerCoordinates.x - SCREEN_CENTER_X
                &&
                coordinates.x  - TILE_SIZE < playerCoordinates.x + SCREEN_CENTER_X
                &&
                coordinates.y  + TILE_SIZE > playerCoordinates.x - SCREEN_CENTER_Y
                &&
                coordinates.y  - TILE_SIZE < playerCoordinates.x + SCREEN_CENTER_Y
        ) {
            final DrawEntityDirectionHandler handler = drawEntityDirectionHandlerRegistry.getHandler(direction);
            BufferedImage image = handler.handle(spriteNumber, images);
//            switch (direction) {
//                case "up":
//                    if (spriteNumber == 1) {
//                        image = up_1;
//                    }
//                    if (spriteNumber == 2) {
//                        image = up_2;
//                    }
//                    break;
//                case "down":
//                    if (spriteNumber == 1) {
//                        image = down_1;
//                    }
//                    if (spriteNumber == 2) {
//                        image = down_2;
//                    }
//                    break;
//                case "left":
//                    if (spriteNumber == 1) {
//                        image = left_1;
//                    }
//                    if (spriteNumber == 2) {
//                        image = left_2;
//                    }
//                    break;
//                case "right":
//                    if (spriteNumber == 1) {
//                        image = right_1;
//                    }
//                    if (spriteNumber == 2) {
//                        image = right_2;
//                    }
//                    break;
//            }
            graphics2D.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public BufferedImage loadScaledImage(String imagePath) {
        ImageScalerServiceImpl tool = new ImageScalerServiceImpl(); //TODO: it is a bean!
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

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }
}
