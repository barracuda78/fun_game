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
    private final KeyItem keyItem_3;

    private final CoinItem coinItem;

    private final DoorItem doorItem_1;
    private final DoorItem doorItem_2;
    private final DoorItem doorItem_3;
    private final DoorItem doorItem_4;
    private final DoorItem doorItem_5;

    private final ChestItem chestItem;

    private final FishItem fishItem;
    private final FishItem fishItem_1;
    private final FishItem fishItem_2;

    private final BootsItem bootsItem_1;
    private final BootsItem bootsItem_2;

    private final SausageItem sausageItem;
    private final SausageItem sausageItem_1;

    private final SausageItem coinItem_1;
    private final SausageItem coinItem_2;

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

//        items[4] = chestItem;
//        items[4].worldX = 40 * TILE_SIZE;
//        items[4].worldY = 7 * TILE_SIZE;

//        items[4] = chestItem;
//        items[4].worldX = 5 * TILE_SIZE;
//        items[4].worldY = 2 * TILE_SIZE;

        items[4] = chestItem;
        items[4].worldX = 22 * TILE_SIZE;
        items[4].worldY = 27 * TILE_SIZE;


        items[5] = fishItem;
        items[5].worldX = 13 * TILE_SIZE;
        items[5].worldY = 9 * TILE_SIZE;

        items[6] = coinItem;
        items[6].worldX = 5 * TILE_SIZE;
        items[6].worldY = 2 * TILE_SIZE;

        items[7] = bootsItem_1;
        items[7].worldX = 10 * TILE_SIZE;
        items[7].worldY = 36 * TILE_SIZE;

        items[8] = sausageItem;
        items[8].worldX = 40 * TILE_SIZE;
        items[8].worldY = 46 * TILE_SIZE;

        items[9] = bootsItem_2;
        items[9].worldX = 40 * TILE_SIZE;
        items[9].worldY = 17 * TILE_SIZE;

        items[10] = fishItem_1;
        items[10].worldX = 15 * TILE_SIZE;
        items[10].worldY = 9 * TILE_SIZE;

        items[11] = fishItem_2;
        items[11].worldX = 12 * TILE_SIZE;
        items[11].worldY = 11 * TILE_SIZE;

        items[12] = sausageItem_1;
        items[12].worldX = 6 * TILE_SIZE;
        items[12].worldY = 44 * TILE_SIZE;

        items[13] = coinItem_1;
        items[13].worldX = 40 * TILE_SIZE;
        items[13].worldY = 7 * TILE_SIZE;

        items[14] = coinItem_2;
        items[14].worldX = 42 * TILE_SIZE;
        items[14].worldY = 10 * TILE_SIZE;

        items[15] = doorItem_3;
        items[15].worldX = 22 * TILE_SIZE;
        items[15].worldY = 29 * TILE_SIZE;

        items[16] = doorItem_4;
        items[16].worldX = 22 * TILE_SIZE;
        items[16].worldY = 30 * TILE_SIZE;

        items[17] = doorItem_5;
        items[17].worldX = 22 * TILE_SIZE;
        items[17].worldY = 31 * TILE_SIZE;

        items[18] = keyItem_3;
        items[18].worldX = 7 * TILE_SIZE;
        items[18].worldY = 46 * TILE_SIZE;
    }

    public Item[] getItems() {
        return items;
    }

}
