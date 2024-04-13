package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class FishItem extends Item {

    public FishItem() {
        name = "fish";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/fish_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fish_01 image not found");
        }
    }

}
