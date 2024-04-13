package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ChestItem extends Item {

    public ChestItem() {
        name = "chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/chest_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("chest_01 image not found");
        }
    }

}
