package com.barracuda.fun.gui.util;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.tile.TileArrayIndexesDto;
import java.awt.Point;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CalculationServiceImpl {

    public Point calculateCoordinates(Point playerCoordinates, int worldColumn, int worldRow) {
        int worldX = worldColumn * TILE_SIZE;
        int worldY = worldRow * TILE_SIZE;
        int screenX = worldX - playerCoordinates.x + SCREEN_CENTER_X;
        int screenY = worldY - playerCoordinates.y + SCREEN_CENTER_Y;
        return new Point(screenX, screenY);
    }

    public TileArrayIndexesDto pointToMapTileArrayIndexes(@NonNull Point point) {
            int x = point.x / TILE_SIZE;
            int y = point.y / TILE_SIZE;
            return new TileArrayIndexesDto(x, y);
    }

}
