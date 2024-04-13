package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class KeyItem extends Item {

    public KeyItem() {
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/key_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("key_01 image not found");
        }
    }

}
