package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.entity.DogNpc;
import com.barracuda.fun.gui.entity.Entity;
import org.springframework.stereotype.Service;

@Service
public class NpcPlacerServiceImpl {

    public void setNpc(CollisionChecker collisionChecker, Entity[] npcs) {
        Entity dog = new DogNpc(collisionChecker);
        dog.worldX = TILE_SIZE * 21;
        dog.worldY = TILE_SIZE * 21;
        npcs[0] = dog;
    }

}
