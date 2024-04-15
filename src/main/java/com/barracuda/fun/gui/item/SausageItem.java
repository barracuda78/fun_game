package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class SausageItem extends Item {

    public SausageItem() {
        name = "sausage";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/sausage_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("sausage_01 image not found");
        }
    }

}
