package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class BootsItem extends Item {

    public BootsItem() {
        name = "boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/boots_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("boots_01 image not found");
        }
    }

}
