package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.item.BootsItem;
import com.barracuda.fun.gui.item.ChestItem;
import com.barracuda.fun.gui.item.CoinItem;
import com.barracuda.fun.gui.item.DoorItem;
import com.barracuda.fun.gui.item.FishItem;
import com.barracuda.fun.gui.item.KeyItem;
import com.barracuda.fun.gui.item.SausageItem;
import org.springframework.stereotype.Service;

@Service
public class ItemPlacerServiceImpl {

    public void setItem(GamePanel gamePanel) {
        gamePanel.items[0] = new KeyItem();
        gamePanel.items[0].worldX = 23 * TILE_SIZE;
        gamePanel.items[0].worldY = 7 * TILE_SIZE;

        gamePanel.items[1] = new KeyItem();
        gamePanel.items[1].worldX = 23 * TILE_SIZE;
        gamePanel.items[1].worldY = 40 * TILE_SIZE;

        gamePanel.items[2] = new DoorItem();
        gamePanel.items[2].worldX = 40 * TILE_SIZE;
        gamePanel.items[2].worldY = 11 * TILE_SIZE;

        gamePanel.items[3] = new DoorItem();
        gamePanel.items[3].worldX = 30 * TILE_SIZE;
        gamePanel.items[3].worldY = 12 * TILE_SIZE;

        gamePanel.items[4] = new ChestItem();
        gamePanel.items[4].worldX = 40 * TILE_SIZE;
        gamePanel.items[4].worldY = 7 * TILE_SIZE;

        gamePanel.items[5] = new FishItem();
        gamePanel.items[5].worldX = 13 * TILE_SIZE;
        gamePanel.items[5].worldY = 9 * TILE_SIZE;

        gamePanel.items[6] = new CoinItem();
        gamePanel.items[6].worldX = 22 * TILE_SIZE;
        gamePanel.items[6].worldY = 27 * TILE_SIZE;

        gamePanel.items[7] = new BootsItem();
        gamePanel.items[7].worldX = 10 * TILE_SIZE;
        gamePanel.items[7].worldY = 36 * TILE_SIZE;

        gamePanel.items[8] = new SausageItem();
        gamePanel.items[8].worldX = 40 * TILE_SIZE;
        gamePanel.items[8].worldY = 46 * TILE_SIZE;

        gamePanel.items[9] = new BootsItem();
        gamePanel.items[9].worldX = 40 * TILE_SIZE;
        gamePanel.items[9].worldY = 17 * TILE_SIZE;
    }

}
