package com.barracuda.fun.gui.entity;


import com.barracuda.fun.enums.MovementDirection;
import com.barracuda.fun.gui.exceptions.MovementDirectionNotFoundException;
import jakarta.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Getter
@Service
@Scope("prototype")
public class Sprites {

    private final List<BufferedImage> images = new ArrayList<>(8);

    private final Map<MovementDirection, List<Integer>> directionToIndexesMap = new HashMap<>();

    @PostConstruct
    public void init() {
        directionToIndexesMap.put(MovementDirection.UP, List.of(0, 1));
        directionToIndexesMap.put(MovementDirection.DOWN, List.of(2, 3));
        directionToIndexesMap.put(MovementDirection.LEFT, List.of(4, 5));
        directionToIndexesMap.put(MovementDirection.RIGHT, List.of(6,7));
    }

    public void addSprite(BufferedImage image, MovementDirection direction, int spriteNumber) {
        final int index = getIndexByDirectionAndSpriteNumber(direction, spriteNumber);
        images.add(index, image);
    }

    private int getIndexByDirectionAndSpriteNumber(MovementDirection direction, int spriteNumber) {
        int index =  directionToIndexesMap.entrySet().stream()
            .filter(e -> direction == e.getKey())
            .map(Entry::getValue)
            .map(list -> spriteNumber == 1 ? list.get(0) : list.get(1))
            .findFirst()
            .orElseThrow(() -> new MovementDirectionNotFoundException("MovementDirection not found: " + direction));
        return index;
    }

    public BufferedImage getUpOneSprite() {
        return images.get(0);
    }

    public BufferedImage getUpTwoSprite() {
        return images.get(1);
    }

    public BufferedImage getDownOneSprite() {
        return images.get(2);
    }

    public BufferedImage getDownTwoSprite() {
        return images.get(3);
    }

    public BufferedImage getLeftOneSprite() {
        return images.get(4);
    }

    public BufferedImage getLeftTwoSprite() {
        return images.get(5);
    }

    public BufferedImage getRightOneSprite() {
        return images.get(6);
    }

    public BufferedImage getRightTwoSprite() {
        return images.get(7);
    }

}
