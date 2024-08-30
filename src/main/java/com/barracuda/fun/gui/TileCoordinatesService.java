package com.barracuda.fun.gui;

import com.barracuda.fun.gui.enums.Direction;
import java.awt.Point;
import lombok.NonNull;

public interface TileCoordinatesService {

  boolean isNearestTileSolid(@NonNull Point point, @NonNull Direction direction);

}
