package com.barracuda.fun.gui.entity;

import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DogNpc extends Entity {

    public DogNpc(CollisionChecker collisionChecker, DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry) {
        super(collisionChecker, drawEntityDirectionHandlerRegistry);
    }

    @PostConstruct
    public void init() {
        direction = "down";
        speed = 1;
        loadDogNPCImage();
    }

    public void loadDogNPCImage() {
        up_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        up_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        down_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        down_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        left_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        left_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        right_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        right_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        images.add(up_1); //TODO do not duplicate code in DogNpc and Player class!
        images.add(up_2);
        images.add(down_1);
        images.add(down_2);
        images.add(left_1);
        images.add(left_2);
        images.add(right_1);
        images.add(right_2);
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
