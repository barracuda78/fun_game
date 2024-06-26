package com.barracuda.fun.gui.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScreenSettings {

    public static final int FPS = 60;
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COLUMN = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2 - (TILE_SIZE / 2);
    public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2 - (TILE_SIZE / 2);

}
