package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class CoinItem extends Item {

    public CoinItem() {
        name = ""
            + "coin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/gold_coin_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("gold_coin_01 image not found");
        }
    }

}
