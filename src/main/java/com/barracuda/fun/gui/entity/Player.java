package com.barracuda.fun.gui.entity;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")

public class Player extends Entity {

    private final GamePanel gamePanel;

    private final KeyHandler keyHandler;

    public Player (GamePanel gamePanel, KeyHandler keyHandler) { //should it depend on GamePanel and KeyHandler?
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() { //do not hardcode. Try to set from properties file? @PostConstruct?
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up_1 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_1.png"));
            up_2 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_2.png"));
            down_1 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_2.png"));
            down_2 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_1.png"));
            left_1 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_1.png"));
            left_2 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_2.png"));
            right_1 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_2.png"));
            right_2 = ImageIO.read(getClass().getResourceAsStream("/graphics/player/cat_down_1.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { //gets called 60 times per second.

        if (keyHandler.downPressed || keyHandler.leftPressed || keyHandler.upPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyHandler.rightPressed) {
                direction = "right";
                x += speed;
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
    }

    public void draw(Graphics2D graphics2D) {
//        graphics2D.setColor(Color.white);
//        graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage image = null;
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
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
