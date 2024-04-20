package com.barracuda.fun.gui.entity.draw_handler;

import com.barracuda.fun.gui.exceptions.DrawEntityDirectionHandlerNotFoundException;
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrawEntityDirectionHandlerDefault implements DrawEntityDirectionHandler {

    @Override
    public boolean canHandle(@NonNull String direction) {
        return false;
    }

    @Override
    public BufferedImage handle(int spriteNumber, List<BufferedImage> images) {
        throw new DrawEntityDirectionHandlerNotFoundException("No DrawEntityDirectionHandler found.");
    }

}
