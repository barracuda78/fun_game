package com.barracuda.fun.gui.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScreenSettings {

    public static final int FPS = 60;
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //16 * 3 = 48
    public static final int MAX_SCREEN_COLUMN = 24;
    public static final int MAX_SCREEN_ROW = 18;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 48 * 16 = 768 (24 1152
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;   // 48 * 12 = 576 (18 864
    public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2 - (TILE_SIZE / 2);
    public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2 - (TILE_SIZE / 2);

}
