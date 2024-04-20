package com.barracuda.fun.gui.item;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.ImageScalerServiceImpl;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class SausageItem extends Item {

    private final ImageScalerServiceImpl tool;

    @PostConstruct
    public void init() {
        name = "sausage";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/objects/sausage_01.png"));
            tool.scaleImage(image, TILE_SIZE, TILE_SIZE);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("sausage_01 image not found");
        }
    }

}
