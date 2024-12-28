package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.Fonts.ARIAL_40_FONT;
import static com.barracuda.fun.gui.constants.Fonts.ARIAL_80_BOLD_FONT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_HEIGHT;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_WIDTH;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.entity.Player;
import com.barracuda.fun.gui.item.BootsItem;
import com.barracuda.fun.gui.item.ChestItem;
import com.barracuda.fun.gui.item.CoinItem;
import com.barracuda.fun.gui.item.FishItem;
import com.barracuda.fun.gui.item.KeyItem;
import com.barracuda.fun.gui.item.SausageItem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UI {

    private final KeyItem keyItem;

    private final CoinItem coinItem;

    private final ChestItem chestItem;

    private final BootsItem bootsItem;

    private final FishItem fishItem;

    private final SausageItem sausageItem;

    private final GameThreadServiceImpl gameThreadService;

    Graphics2D graphics2D;

    public boolean isMessageOn = false;

    public String message = "";

    int messageLifeTime = 0;

    int bootsLifeTime = 0;

    public boolean gameFinished = false;

    double playTime;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public void showMessage(String text) {
        message = text;
        isMessageOn = true;
    }

    public void draw(Graphics2D g2, Player player) {
        this.graphics2D = g2; //TODO: do not initialize it like this!

        //old stuff:
        if (gameFinished) {

            graphics2D.setFont(ARIAL_40_FONT);
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

            graphics2D.setFont(ARIAL_80_BOLD_FONT);
            graphics2D.setColor(Color.YELLOW);
            text = "YOU WIN!";
            x = getXForCenterText(text);
            y = SCREEN_HEIGHT / 2 + (TILE_SIZE * 2);
            graphics2D.drawString(text, x, y);

            gameThreadService.stopGameThread();

        } else {
            graphics2D.setFont(ARIAL_40_FONT);
            graphics2D.setColor(Color.WHITE);

            graphics2D.drawImage(keyItem.image, TILE_SIZE / 2, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + player.keyAmount, 74, 60);

            graphics2D.drawImage(coinItem.image, TILE_SIZE * 3, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + player.coinAmount, 192, 60);

            graphics2D.drawImage(chestItem.image, TILE_SIZE * 6, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + player.chestAmount, 340, 60);

            graphics2D.drawImage(fishItem.image, TILE_SIZE * 9, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + player.fishAmount, 484, 60);

            graphics2D.drawImage(sausageItem.image, TILE_SIZE * 12, TILE_SIZE / 2,
                TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawString(": " + player.sausageAmount, 628, 60);

            playTime += (double) 1/60;
            graphics2D.drawString("Time:" + decimalFormat.format(playTime), TILE_SIZE * 20, TILE_SIZE * 17);

            if (player.bootsOn) {
                graphics2D.drawImage(bootsItem.image, TILE_SIZE / 2, TILE_SIZE * 11,
                    TILE_SIZE, TILE_SIZE, null);
                graphics2D.drawString(": " + (600 - bootsLifeTime), 74, 576);
                bootsLifeTime++;
                if (bootsLifeTime > 600) {
                    player.bootsOn = false;
                    player.speed -= 4;
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
