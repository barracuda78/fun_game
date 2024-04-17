package com.barracuda.fun.gui.tile;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;
import static com.barracuda.fun.gui.constants.WorldSettings.MAX_WORLD_COLUMN;
import static com.barracuda.fun.gui.constants.WorldSettings.MAX_WORLD_ROW;

import com.barracuda.fun.gui.GamePanel;
import com.barracuda.fun.gui.ImageScalerServiceImpl;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class TileManager {

    GamePanel gamePanel;

    public Tile[] tile;

    public int[][] mapTile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[99];
        mapTile = new int[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        getTileImage();
//        loadMap("/graphics/maps/world_01.txt");
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

    public void draw(Graphics2D graphics2D) {

        int worldColumn = 0;
        int worldRow = 0;


        while (worldColumn < MAX_WORLD_COLUMN && worldRow < MAX_WORLD_ROW) {
            int tileNum = mapTile[worldColumn][worldRow];

            int worldX = worldColumn * TILE_SIZE;
            int worldY = worldRow * TILE_SIZE;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (
                worldX + TILE_SIZE > gamePanel.player.worldX - gamePanel.player.screenX
                    &&
                worldX  - TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX
                    &&
                worldY  + TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY
                    &&
                worldY  - TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY
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
