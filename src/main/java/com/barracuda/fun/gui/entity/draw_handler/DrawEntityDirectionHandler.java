package com.barracuda.fun.gui.entity.draw_handler;

import java.awt.image.BufferedImage;
import java.util.List;
import lombok.NonNull;

public interface DrawEntityDirectionHandler {

    boolean canHandle(@NonNull String direction);

    BufferedImage handle(int spriteNumber, List<BufferedImage> images);

}
