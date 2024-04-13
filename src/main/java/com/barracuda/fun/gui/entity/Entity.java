package com.barracuda.fun.gui.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

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

    public Rectangle solidArea;

    public int solidAreaDefaultX;

    public int solidAreaDefaultY;

    public boolean collisionOn = false;

}
