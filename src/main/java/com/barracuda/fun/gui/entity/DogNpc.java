package com.barracuda.fun.gui.entity;

import com.barracuda.fun.enums.MovementDirection;
import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import jakarta.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DogNpc extends Entity {

    public DogNpc(CollisionChecker collisionChecker, DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry, Sprites sprites) {
        super(collisionChecker, drawEntityDirectionHandlerRegistry, sprites);
    }

    @PostConstruct
    public void init() {
        direction = "down";
        speed = 1;
        loadDogNPCImage();
    }

    public void loadDogNPCImage() {
        BufferedImage up_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        BufferedImage up_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        BufferedImage down_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        BufferedImage down_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        BufferedImage left_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        BufferedImage left_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        BufferedImage right_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        BufferedImage right_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        sprites.addSprite(up_1, MovementDirection.UP, 1); //TODO do not duplicate code in DogNpc and Player class!
        sprites.addSprite(up_2, MovementDirection.UP, 2);
        sprites.addSprite(down_1, MovementDirection.UP, 1);
        sprites.addSprite(down_2, MovementDirection.UP, 2);
        sprites.addSprite(left_1, MovementDirection.UP, 1);
        sprites.addSprite(left_2, MovementDirection.UP, 2);
        sprites.addSprite(right_1, MovementDirection.UP, 1);
        sprites.addSprite(right_2, MovementDirection.UP, 2);
    }

    //AI method of characters behavior:
    @Override
    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if( i < 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if ( i > 50 && i <= 75) {
                direction = "left";
            }
            if ( i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }

}
