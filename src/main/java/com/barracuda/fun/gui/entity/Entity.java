package com.barracuda.fun.gui.entity;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.ImageScalerServiceImpl;
import com.barracuda.fun.gui.PlayerCoordinatesService;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandler;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import com.barracuda.fun.gui.item.Item;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {

    private final DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry;
    final CollisionChecker collisionChecker;
    private final Point coordinates;
    public int speed;

    protected final Sprites sprites;


    public String direction;

    public int spriteCounter = 0; //todo: move it to Sprites class with its methods?

    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0,0,40,40);

    public int solidAreaDefaultX;

    public int solidAreaDefaultY;

    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public Entity(
        CollisionChecker collisionChecker,
        DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry,
        Sprites sprites
    ) {
        this.collisionChecker = collisionChecker;
        this.drawEntityDirectionHandlerRegistry = drawEntityDirectionHandlerRegistry;
        this.coordinates = new Point();
        this.sprites = sprites;
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
            final BufferedImage image = handler.handle(spriteNumber, sprites.getImages());
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
