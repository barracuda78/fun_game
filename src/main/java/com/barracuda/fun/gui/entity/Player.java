package com.barracuda.fun.gui.entity;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.KeyHandler;
import com.barracuda.fun.gui.UtilityTool;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class Player extends Entity {

    private final GamePanel gamePanel;

    private final KeyHandler keyHandler;

    public final int screenX;

    public final int screenY;

    public int keyAmount = 0;

    public int coinAmount = 0;

    public int chestAmount = 0;

    public int fishAmount = 0;

    public int sausageAmount = 0;

    public boolean bootsOn = false;

    public Player (GamePanel gamePanel, KeyHandler keyHandler) { //should it depend on GamePanel and KeyHandler?
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 32;
        solidArea.width = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() { //do not hardcode. Try to set from properties file? @PostConstruct?
        worldX = gamePanel.tileSize * 23; //players position on the world map
        worldY = gamePanel.tileSize * 21; //players position on the world map
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up_1 = setup("white_cat_up_1");
        up_2 = setup("white_cat_up_2");
        down_1 = setup("white_cat_down_1");
        down_2 = setup("white_cat_down_2");
        left_1 = setup("white_cat_left_1");
        left_2 = setup("white_cat_left_2");
        right_1 = setup("white_cat_right_1");
        right_2 = setup("white_cat_right_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool tool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/graphics/player/" + imageName + ".png"));
            scaledImage = tool.scaleImage(scaledImage, gamePanel.tileSize, gamePanel.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void update() { //gets called 60 times per second.

        if (keyHandler.downPressed || keyHandler.leftPressed || keyHandler.upPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";

            } else if (keyHandler.downPressed) {
                direction = "down";

            } else if (keyHandler.leftPressed) {
                direction = "left";

            } else if (keyHandler.rightPressed) {
                direction = "right";

            }

            // Check tile collision:
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // Check item collision:
            int itemIndex = gamePanel.collisionChecker.checkItem(this, true);
            pickUpItem(itemIndex);

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
    }

    public void pickUpItem(int index) {
        if (index != 999) {
            String itemName = gamePanel.items[index].name;
            switch (itemName) {
                case "key":
                    gamePanel.playSoundEffect(2);
                    keyAmount++;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You got a key!");
                    break;
                case "door":
                    if(keyAmount > 0) {
                        gamePanel.playSoundEffect(3);
                        gamePanel.items[index] = null;
                        keyAmount--;
                        gamePanel.ui.showMessage("You have opened the door!");
                    }
                    else {
                        gamePanel.ui.showMessage("You need a key.");
                    }
                    break;
                case "chest":
                    gamePanel.playSoundEffect(2);
                    chestAmount++;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You've got a chest!");
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                    break;
                case "fish":
                    gamePanel.playSoundEffect(2);
                    fishAmount++;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You got a fish!");
                    break;
                case "coin":
                    gamePanel.playSoundEffect(2);
                    coinAmount++;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You got a coin!");
                    break;
                case "boots":
                    gamePanel.playSoundEffect(1);
                    bootsOn = true;
                    speed += 4;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You got speed boots!");
                    break;
                case "sausage":
                    gamePanel.playSoundEffect(2);
                    sausageAmount++;
                    gamePanel.items[index] = null;
                    gamePanel.ui.showMessage("You have eaten sausage!");
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
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
        graphics2D.drawImage(image, screenX, screenY, null);
    }

}
