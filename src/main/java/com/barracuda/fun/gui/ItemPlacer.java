package com.barracuda.fun.gui;

import com.barracuda.fun.gui.item.BootsItem;
import com.barracuda.fun.gui.item.ChestItem;
import com.barracuda.fun.gui.item.CoinItem;
import com.barracuda.fun.gui.item.DoorItem;
import com.barracuda.fun.gui.item.FishItem;
import com.barracuda.fun.gui.item.KeyItem;
import com.barracuda.fun.gui.item.SausageItem;

public class ItemPlacer {

    public GamePanel gamePanel;

    public ItemPlacer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setItem() {
        gamePanel.items[0] = new KeyItem(gamePanel);
        gamePanel.items[0].worldX = 23 * gamePanel.tileSize;
        gamePanel.items[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.items[1] = new KeyItem(gamePanel);
        gamePanel.items[1].worldX = 23 * gamePanel.tileSize;
        gamePanel.items[1].worldY = 40 * gamePanel.tileSize;

        gamePanel.items[2] = new DoorItem();
        gamePanel.items[2].worldX = 40 * gamePanel.tileSize;
        gamePanel.items[2].worldY = 11 * gamePanel.tileSize;

        gamePanel.items[3] = new DoorItem();
        gamePanel.items[3].worldX = 30 * gamePanel.tileSize;
        gamePanel.items[3].worldY = 12 * gamePanel.tileSize;

        gamePanel.items[4] = new ChestItem();
        gamePanel.items[4].worldX = 40 * gamePanel.tileSize;
        gamePanel.items[4].worldY = 7 * gamePanel.tileSize;

        gamePanel.items[5] = new FishItem();
        gamePanel.items[5].worldX = 13 * gamePanel.tileSize;
        gamePanel.items[5].worldY = 9 * gamePanel.tileSize;

        gamePanel.items[6] = new CoinItem();
        gamePanel.items[6].worldX = 22 * gamePanel.tileSize;
        gamePanel.items[6].worldY = 27 * gamePanel.tileSize;

        gamePanel.items[7] = new BootsItem();
        gamePanel.items[7].worldX = 10 * gamePanel.tileSize;
        gamePanel.items[7].worldY = 36 * gamePanel.tileSize;

        gamePanel.items[8] = new SausageItem();
        gamePanel.items[8].worldX = 40 * gamePanel.tileSize;
        gamePanel.items[8].worldY = 46 * gamePanel.tileSize;

        gamePanel.items[9] = new BootsItem();
        gamePanel.items[9].worldX = 40 * gamePanel.tileSize;
        gamePanel.items[9].worldY = 17 * gamePanel.tileSize;
    }

}
