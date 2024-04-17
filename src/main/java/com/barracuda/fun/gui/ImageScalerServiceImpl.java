package com.barracuda.fun.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Service;

@Service
public class ImageScalerServiceImpl {

    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalImage, 0,0 , width, height, null);
        graphics2D.dispose();
        return scaledImage;
    }

}
