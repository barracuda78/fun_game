package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.entity.DogNpc;
import com.barracuda.fun.gui.entity.Entity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NpcPlacerServiceImpl {

    @Qualifier("dogNpc")
    private final Entity dog_1;

    private final Entity[] npcs = new Entity[10];

    public NpcPlacerServiceImpl(DogNpc dog) {
        this.dog_1 = dog;
    }

    public void placeNpc() {
        dog_1.setCoordinates(TILE_SIZE * 21, TILE_SIZE * 21);
        npcs[0] = dog_1;
    }

    public final Entity[] getNpcs() {
        return npcs;
    }

}
