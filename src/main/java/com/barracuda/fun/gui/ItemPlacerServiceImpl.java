package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.item.BootsItem;
import com.barracuda.fun.gui.item.ChestItem;
import com.barracuda.fun.gui.item.CoinItem;
import com.barracuda.fun.gui.item.DoorItem;
import com.barracuda.fun.gui.item.FishItem;
import com.barracuda.fun.gui.item.Item;
import com.barracuda.fun.gui.item.KeyItem;
import com.barracuda.fun.gui.item.SausageItem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPlacerServiceImpl {

    private final KeyItem keyItem_1;

    private final KeyItem keyItem_2;

    private final CoinItem coinItem;

    private final DoorItem doorItem_1;
    private final DoorItem doorItem_2;
    private final ChestItem chestItem;
    private final FishItem fishItem;
    private final BootsItem bootsItem_1;
    private final BootsItem bootsItem_2;
    private final SausageItem sausageItem;

    private final Item[] items = new Item[99];

    public void setItem() {
        items[0] = keyItem_1;
        items[0].worldX = 23 * TILE_SIZE;
        items[0].worldY = 7 * TILE_SIZE;

        items[1] = keyItem_2;
        items[1].worldX = 23 * TILE_SIZE;
        items[1].worldY = 40 * TILE_SIZE;

        items[2] = doorItem_1;
        items[2].worldX = 40 * TILE_SIZE;
        items[2].worldY = 11 * TILE_SIZE;

        items[3] = doorItem_2;
        items[3].worldX = 30 * TILE_SIZE;
        items[3].worldY = 12 * TILE_SIZE;

        items[4] = chestItem;
        items[4].worldX = 40 * TILE_SIZE;
        items[4].worldY = 7 * TILE_SIZE;

        items[5] = fishItem;
        items[5].worldX = 13 * TILE_SIZE;
        items[5].worldY = 9 * TILE_SIZE;

        items[6] = coinItem;
        items[6].worldX = 22 * TILE_SIZE;
        items[6].worldY = 27 * TILE_SIZE;

        items[7] = bootsItem_1;
        items[7].worldX = 10 * TILE_SIZE;
        items[7].worldY = 36 * TILE_SIZE;

        items[8] = sausageItem;
        items[8].worldX = 40 * TILE_SIZE;
        items[8].worldY = 46 * TILE_SIZE;

        items[9] = bootsItem_2;
        items[9].worldX = 40 * TILE_SIZE;
        items[9].worldY = 17 * TILE_SIZE;
    }

    public Item[] getItems() {
        return items;
    }

}
