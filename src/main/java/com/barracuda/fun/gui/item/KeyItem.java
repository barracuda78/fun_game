package com.barracuda.fun.gui.item;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KeyItem extends Item {

    GamePanel gamePanel;

    public KeyItem(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/key_01.png"));
            tool.scaleImage(image, TILE_SIZE, TILE_SIZE);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("key_01 image not found");
        }
    }

}
