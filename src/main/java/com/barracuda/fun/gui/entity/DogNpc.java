package com.barracuda.fun.gui.entity;

import com.barracuda.fun.gui.CollisionChecker;
import java.util.Random;

public class DogNpc extends Entity {

    public DogNpc(CollisionChecker collisionChecker) {
        super(collisionChecker);
        direction = "down";
        speed = 1;
        getImage();
    }

    public void getImage() {
        up_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        up_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        down_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        down_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        left_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        left_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        right_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        right_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
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
