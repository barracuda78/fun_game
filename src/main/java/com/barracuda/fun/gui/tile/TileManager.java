package com.barracuda.fun.gui.tile;

import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_X;
import static com.barracuda.fun.gui.constants.ScreenSettings.SCREEN_CENTER_Y;
import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;
import static com.barracuda.fun.gui.constants.WorldSettings.MAX_WORLD_COLUMN;
import static com.barracuda.fun.gui.constants.WorldSettings.MAX_WORLD_ROW;

import com.barracuda.fun.gui.ImageScalerServiceImpl;
import com.barracuda.fun.gui.entity.Player;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class TileManager {

    public Tile[] tile;

    public int[][] mapTile;

    public TileManager() {
        tile = new Tile[99];
        mapTile = new int[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        getTileImage();
        loadMap("/graphics/maps/world_01_enhanced.txt");
    }

    public void getTileImage() {

        setup(0, "tile_brick", true);
        setup(1, "tile_grass_02", false);
        setup(2, "tile_water", true);
        setup(3, "earth", false);
        setup(4, "tree_02", true);
        setup(5, "sand", false);
        setup(6, "palm_01", true);

        // WATER:
        setup(10, "tile_water_01", true);
        setup(11, "tile_water_02", true);

        //GRASS WATER:
        setup(20, "tile_grass_water_up_01", false);
        setup(21, "tile_grass_water_up_right_01", false);
        setup(22, "tile_grass_water_right_01", false);
        setup(23, "tile_grass_water_down_right_01", false);
        setup(24, "tile_grass_water_down_01", false);
        setup(25, "tile_grass_water_down_left_01", false);
        setup(26, "tile_grass_water_left_01", false);
        setup(27, "tile_grass_water_up_left_01", false);
        setup(28, "tile_grass_water_up_left_outer_01", false);
        setup(29, "tile_grass_water_up_right_outer_01", false);
        setup(30, "tile_grass_water_down_right_outer_01", false);
        setup(31, "tile_grass_water_down_left_outer_01", false);

    }


    public void setup(int index, String imageName, boolean collision) {
        ImageScalerServiceImpl tool = new ImageScalerServiceImpl();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/" + imageName + ".png"));
            tile[index].image = tool.scaleImage(tile[index].image, TILE_SIZE, TILE_SIZE);
            tile[index].collision = collision;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @PostConstruct
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col < MAX_WORLD_COLUMN && row < MAX_WORLD_ROW) {
                String line = br.readLine();
                while(col < MAX_WORLD_COLUMN) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTile[col][row] = num;
                    col++;
                }
                if(col == MAX_WORLD_COLUMN) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: do not pass a player here. You need only screenX and screenY to draw. So calculate them in a separate class and pass them here.
    public void draw(Graphics2D graphics2D, Player player) {

        int worldColumn = 0;
        int worldRow = 0;


        while (worldColumn < MAX_WORLD_COLUMN && worldRow < MAX_WORLD_ROW) {
            int tileNum = mapTile[worldColumn][worldRow];

            int worldX = worldColumn * TILE_SIZE;
            int worldY = worldRow * TILE_SIZE;
            int screenX = worldX - player.worldX + SCREEN_CENTER_X;
            int screenY = worldY - player.worldY + SCREEN_CENTER_Y;

            if (
                worldX + TILE_SIZE > player.worldX - SCREEN_CENTER_X
                    &&
                worldX  - TILE_SIZE < player.worldX + SCREEN_CENTER_X
                    &&
                worldY  + TILE_SIZE > player.worldY - SCREEN_CENTER_Y
                    &&
                worldY  - TILE_SIZE < player.worldY + SCREEN_CENTER_Y
            ) {
                graphics2D.drawImage(tile[tileNum].image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
            }
            worldColumn++;
            if (worldColumn == MAX_WORLD_COLUMN) {
                worldColumn = 0;
                worldRow++;
            }
        }

    }

}
