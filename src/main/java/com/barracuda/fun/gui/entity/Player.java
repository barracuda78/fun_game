package com.barracuda.fun.gui.entity;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;

import com.barracuda.fun.enums.MovementDirection;
import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.KeyHandler;
import com.barracuda.fun.gui.SoundServiceImpl;
import com.barracuda.fun.gui.UI;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import com.barracuda.fun.gui.item.Item;
import jakarta.annotation.PostConstruct;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

@Component
public class Player extends Entity { //TODO: should not inherit from Entity!

    private final KeyHandler keyHandler;

    private final SoundServiceImpl soundService;

    private final UI ui;

    public int keyAmount = 0;

    public int coinAmount = 0;

    public int chestAmount = 0;

    public int fishAmount = 0;

    public int sausageAmount = 0;

    public boolean bootsOn = false;

    public Player (CollisionChecker collisionChecker, KeyHandler keyHandler, SoundServiceImpl soundService, UI ui, DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry, Sprites sprites) { //TODO: should it depend on and KeyHandler?
        super(collisionChecker, drawEntityDirectionHandlerRegistry, sprites);
        this.keyHandler = keyHandler;
        this.soundService = soundService;
        this.ui = ui;
    }

    @PostConstruct
    public void init() {
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 40;
        solidArea.width = 40;
        setDefaultValues();
        setCoordinates(TILE_SIZE * 23, TILE_SIZE * 21);
        loadPlayerImage();
    }

    public void setDefaultValues() { //do not hardcode. Try to set from properties file? @PostConstruct?
        speed = 4;
        direction = "down";
    }

    public void loadPlayerImage() {
        BufferedImage up_1 = loadScaledImage("/graphics/player/white_cat_up_1.png");
        BufferedImage up_2 = loadScaledImage("/graphics/player/white_cat_up_2.png");
        BufferedImage down_1 = loadScaledImage("/graphics/player/white_cat_down_1.png");
        BufferedImage down_2 = loadScaledImage("/graphics/player/white_cat_down_2.png");
        BufferedImage left_1 = loadScaledImage("/graphics/player/white_cat_left_1.png");
        BufferedImage left_2 = loadScaledImage("/graphics/player/white_cat_left_2.png");
        BufferedImage right_1 = loadScaledImage("/graphics/player/white_cat_right_1.png");
        BufferedImage right_2 = loadScaledImage("/graphics/player/white_cat_right_2.png");
        sprites.addSprite(up_1, MovementDirection.UP, 1);
        sprites.addSprite(up_2, MovementDirection.UP, 2);
        sprites.addSprite(down_1, MovementDirection.DOWN, 1);
        sprites.addSprite(down_2, MovementDirection.DOWN, 2);
        sprites.addSprite(left_1, MovementDirection.LEFT, 1);
        sprites.addSprite(left_2, MovementDirection.LEFT, 2);
        sprites.addSprite(right_1, MovementDirection.RIGHT, 1);
        sprites.addSprite(right_2, MovementDirection.RIGHT, 2);
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
                        getCoordinates().y -= speed;
                        break;
                    case "down":
                        getCoordinates().y += speed;
                        break;
                    case "left":
                        getCoordinates().x -= speed;
                        break;
                    case "right":
                        getCoordinates().x += speed;
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
                    image = sprites.getUpOneSprite();
                }
                if (spriteNumber == 2) {
                    image = sprites.getUpTwoSprite();
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = sprites.getDownOneSprite();
                }
                if (spriteNumber == 2) {
                    image = sprites.getDownTwoSprite();
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = sprites.getLeftOneSprite();
                }
                if (spriteNumber == 2) {
                    image = sprites.getLeftTwoSprite();
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = sprites.getRightOneSprite();
                }
                if (spriteNumber == 2) {
                    image = sprites.getRightTwoSprite();
                }
                break;
        }
        graphics2D.drawImage(image, SCREEN_CENTER_X, SCREEN_CENTER_Y, null);
    }

}
