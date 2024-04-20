package com.barracuda.fun.gui.entity.draw_handler;



import com.barracuda.fun.enums.MovementDirection;
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrawEntityDirectionHandlerUp implements DrawEntityDirectionHandler {

    @Override
    public boolean canHandle(@NonNull String direction) {
        return MovementDirection.UP.getCode().equals(direction);
    }

    @Override
    public BufferedImage handle(int spriteNumber, List<BufferedImage> images) {
        BufferedImage image = null;
        if (spriteNumber == 1) {
            image = images.get(0);
        }
        if (spriteNumber == 2) {
            image = images.get(1);
        }
        return image;
    }

}
