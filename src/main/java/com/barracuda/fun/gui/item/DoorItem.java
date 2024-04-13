package com.barracuda.fun.gui.item;

import java.io.IOException;
import javax.imageio.ImageIO;

public class DoorItem extends Item {

    public DoorItem() {
        name = "door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/door_01.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("door_01 image not found");
        }
        supportsCollision = true;
    }

}
