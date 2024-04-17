package com.barracuda.fun;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_HEIGHT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_WIDTH;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.item.BootsItem;
import com.barracuda.fun.gui.item.ChestItem;
import com.barracuda.fun.gui.item.CoinItem;
import com.barracuda.fun.gui.item.FishItem;
import com.barracuda.fun.gui.item.KeyItem;
import com.barracuda.fun.gui.item.SausageItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    Graphics2D graphics2D;

    GamePanel gamePanel;

    Font arial_40_font;

    Font arial_80_bold_font;

    BufferedImage keyImage;
    BufferedImage coinImage;
    BufferedImage chestImage;
    BufferedImage bootsImage;
    BufferedImage fishImage;

    BufferedImage sausageImage;

    public boolean isMessageOn = false;

    public String message = "";

    int messageLifeTime = 0;

    int bootsLifeTime = 0;

    public boolean gameFinished = false;

    double playTime;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40_font = new Font("Arial", Font.PLAIN, 40);
        arial_80_bold_font = new Font("Arial", Font.BOLD, 80);
        KeyItem keyItem = new KeyItem(gamePanel);
        keyImage = keyItem.image;
        CoinItem coinItem = new CoinItem();
        coinImage = coinItem.image;
        ChestItem chestItem = new ChestItem();
        chestImage = chestItem.image;
        BootsItem bootsItem = new BootsItem();
        bootsImage = bootsItem.image;
        FishItem fishItem = new FishItem();
        fishImage = fishItem.image;
        SausageItem sausageItem = new SausageItem();
        sausageImage = sausageItem.image;
    }

    public void showMessage(String text) {
        message = text;
        isMessageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.graphics2D = g2; //TODO: do not initialize it like this!

        //old stuff:
        if (gameFinished) {

            graphics2D.setFont(arial_40_font);
            graphics2D.setColor(Color.WHITE);

            //center coordinates:
            String text = "You won the game!";
            int x = getXForCenterText(text);
            int y = SCREEN_HEIGHT / 2 - (TILE_SIZE * 3);
            graphics2D.drawString(text, x, y);

            text = "Your time is: " + decimalFormat.format(playTime);
            x = getXForCenterText(text);
            y = SCREEN_HEIGHT / 2 + (TILE_SIZE * 4);
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(arial_80_bold_font);
            graphics2D.setColor(Color.YELLOW);
            text = "YOU WIN!";
            x = getXForCenterText(text);
            y = SCREEN_HEIGHT / 2 + (TILE_SIZE * 2);
            graphics2D.drawString(text, x, y);

            gamePanel.gameThread = null;

        } else {
            graphics2D.setFont(arial_40_font);
            graphics2D.setColor(Color.WHITE);

            graphics2D.drawImage(keyImage, TILE_SIZE / 2, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + gamePanel.player.keyAmount, 74, 60);

            graphics2D.drawImage(coinImage, TILE_SIZE * 3, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + gamePanel.player.coinAmount, 192, 60);

            graphics2D.drawImage(chestImage, TILE_SIZE * 6, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + gamePanel.player.chestAmount, 340, 60);

            graphics2D.drawImage(fishImage, TILE_SIZE * 9, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + gamePanel.player.fishAmount, 484, 60);

            graphics2D.drawImage(sausageImage, TILE_SIZE * 12, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + gamePanel.player.sausageAmount, 628, 60);

            playTime += (double) 1/60;
            graphics2D.drawString("Time:" + decimalFormat.format(playTime), TILE_SIZE * 11, TILE_SIZE
                * 11);

            if (gamePanel.player.bootsOn) {
                graphics2D.drawImage(bootsImage, TILE_SIZE / 2, TILE_SIZE * 11,
                    TILE_SIZE, TILE_SIZE, null);
                graphics2D.drawString(": " + (600 - bootsLifeTime), 74, 576);
                bootsLifeTime++;
                if (bootsLifeTime > 600) {
                    gamePanel.player.bootsOn = false;
                    gamePanel.player.speed -= 4;
                    bootsLifeTime = 0;
                }
            }

            if (isMessageOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30f));
                graphics2D.drawString(message, TILE_SIZE / 2, TILE_SIZE * 5);
                messageLifeTime++;
                if (messageLifeTime > 120) {
                    messageLifeTime = 0;
                    isMessageOn = false;
                }
            }
        }

    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenterText(text);
        int y = SCREEN_HEIGHT / 2;
        graphics2D.drawString(text, x, y);

    }

    public int getXForCenterText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return SCREEN_WIDTH / 2 - length / 2;
    }

}
