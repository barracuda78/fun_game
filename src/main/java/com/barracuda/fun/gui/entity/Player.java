package com.barracuda.fun.gui.entity;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_HEIGHT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_WIDTH;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;

import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.KeyHandler;
import com.barracuda.fun.gui.SoundServiceImpl;
import com.barracuda.fun.gui.UI;
import com.barracuda.fun.gui.item.Item;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

@Component
public class Player extends Entity {

    private final KeyHandler keyHandler;

    private final SoundServiceImpl soundService;

    private final UI ui;

    public int keyAmount = 0;

    public int coinAmount = 0;

    public int chestAmount = 0;

    public int fishAmount = 0;

    public int sausageAmount = 0;

    public boolean bootsOn = false;

    public Player (CollisionChecker collisionChecker, KeyHandler keyHandler, SoundServiceImpl soundService, UI ui) { //should it depend on GamePanel and KeyHandler?
        super(collisionChecker);
        this.keyHandler = keyHandler;
        this.soundService = soundService;
        this.ui = ui;
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
        worldX = TILE_SIZE * 23; //players position on the world map
        worldY = TILE_SIZE * 21; //players position on the world map
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up_1 = setup("/graphics/player/white_cat_up_1.png");
        up_2 = setup("/graphics/player/white_cat_up_2.png");
        down_1 = setup("/graphics/player/white_cat_down_1.png");
        down_2 = setup("/graphics/player/white_cat_down_2.png");
        left_1 = setup("/graphics/player/white_cat_left_1.png");
        left_2 = setup("/graphics/player/white_cat_left_2.png");
        right_1 = setup("/graphics/player/white_cat_right_1.png");
        right_2 = setup("/graphics/player/white_cat_right_2.png");
    }

    public void update(Item[] items, Entity[] npcs) { //gets called 60 times per second.

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
            collisionChecker.checkTile(this);

            // Check item collision:
            int itemIndex = collisionChecker.checkItem(this, true, items);
            pickUpItem(itemIndex, items);

            int npcIndex = collisionChecker.checkEntity(this, npcs);
            interactNpc(npcIndex);

            if (!collisionOn) {
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

    public void pickUpItem(int index, Item[] items) {
        if (index != 999) {
            String itemName = items[index].name;
            switch (itemName) {
                case "key":
                    soundService.playSoundEffect(2);
                    keyAmount++;
                    items[index] = null;
                    ui.showMessage("You got a key!");
                    break;
                case "door":
                    if(keyAmount > 0) {
                        soundService.playSoundEffect(3);
                        items[index] = null;
                        keyAmount--;
                        ui.showMessage("You have opened the door!");
                    }
                    else {
                        ui.showMessage("You need a key.");
                    }
                    break;
                case "chest":
                    soundService.playSoundEffect(2);
                    chestAmount++;
                    items[index] = null;
                    ui.showMessage("You've got a chest!");
                    ui.gameFinished = true;
                    soundService.stopMusic();
                    soundService.playSoundEffect(4);
                    break;
                case "fish":
                    soundService.playSoundEffect(2);
                    fishAmount++;
                    items[index] = null;
                    ui.showMessage("You got a fish!");
                    break;
                case "coin":
                    soundService.playSoundEffect(2);
                    coinAmount++;
                    items[index] = null;
                    ui.showMessage("You got a coin!");
                    break;
                case "boots":
                    soundService.playSoundEffect(1);
                    bootsOn = true;
                    speed += 4;
                    items[index] = null;
                    ui.showMessage("You got speed boots!");
                    break;
                case "sausage":
                    soundService.playSoundEffect(2);
                    sausageAmount++;
                    items[index] = null;
                    ui.showMessage("You have eaten sausage!");
                    break;
            }
        }
    }

    public void interactNpc(int index) {
        if (index != 999) {
            System.out.println("=========you are hitting an NPC!========");
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
        graphics2D.drawImage(image, SCREEN_CENTER_X, SCREEN_CENTER_Y, null);
    }

}
