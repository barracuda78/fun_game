package com.barracuda.fun.gui;


import com.barracuda.fun.gui.enums.Direction;
import com.barracuda.fun.gui.tile.Tile;
import com.barracuda.fun.gui.tile.TileArrayIndexesDto;
import com.barracuda.fun.gui.tile.TileManager;
import com.barracuda.fun.gui.util.CalculationServiceImpl;
import java.awt.Point;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TileCoordinatesServiceImpl implements TileCoordinatesService {

  private final TileManager tileManager;

  private final CalculationServiceImpl calculationService;

  @Override
  public boolean isNearestTileSolid(@NonNull Point point, @NonNull Direction direction) {
    final int[][] mapTile = tileManager.mapTileArray;
    TileArrayIndexesDto dto = calculationService.pointToMapTileArrayIndexes(point);
    int x = 0;
    int y = 0;

    switch (direction) {
      case RIGHT -> {
        x = Math.min(dto.x() + 1, mapTile.length);
        y = dto.y();
        break;
      }
      case LEFT -> {
        x = Math.max(dto.x() - 1, 0);
        y = dto.y();
        break;
      }
      case UP -> {
        x = dto.x();
        y = Math.max(dto.y() - 1, 0);
        break;
      }
      case DOWN -> {
        x = dto.x();
        y = Math.min(dto.y() + 1, mapTile[0].length);
        break;
      }
    }

    final int tileNumber = mapTile[x][y];
    final Tile[] tiles = tileManager.tileArray;
    final Tile tile = tiles[tileNumber];
    return tile.collision;
  }

}
