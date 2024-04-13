package com.barracuda.fun.gui.tile;

import com.barracuda.fun.gui.GamePanel;
import jakarta.annotation.PostConstruct;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
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
        tile = new Tile[10];
        mapTile = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/graphics/maps/world_01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_brick.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_grass.png"));
            tile[1].collision = false;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/sand.png"));
        }
        catch (IOException e) {
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
            while(col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                while(col < gamePanel.maxWorldColumn) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTile[col][row] = num;
                    col++;
                }
                if(col == gamePanel.maxWorldColumn) {
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


        while (worldColumn < gamePanel.maxWorldColumn && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTile[worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                    &&
                worldX  - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                    &&
                worldY  + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                    &&
                worldY  - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                graphics2D.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldColumn++;
            if (worldColumn == gamePanel.maxWorldColumn) {
                worldColumn = 0;
                worldRow++;
            }
        }



//        //brick:
//        for (int x = 0; x < 16*48; x+=48) {
//            graphics2D.drawImage(tile[2].image, x, 0, gamePanel.tileSize, gamePanel.tileSize, null);
//        }
//        for (int y = 48; y < 11*48; y+=48) {
//            graphics2D.drawImage(tile[2].image, 0, y, gamePanel.tileSize, gamePanel.tileSize, null);
//        }
//        for (int y = 48; y < 11*48; y+=48) {
//            graphics2D.drawImage(tile[2].image, 15*48, y, gamePanel.tileSize, gamePanel.tileSize, null);
//        }
//        for (int x = 0; x < 16*48; x+=48) {
//            graphics2D.drawImage(tile[2].image, x, 11*48, gamePanel.tileSize, gamePanel.tileSize, null);
//        }
//
//        //grass:
//        for (int y = 48; y < 11*48; y+=48) {
//            for (int x = 48; x < 15 * 48; x += 48) {
//                graphics2D.drawImage(tile[0].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
//            }
//        }
//
//        //water:
//        for (int y = 48*5; y < 8*48; y+=48) {
//            for (int x = 48 * 4; x < 12 * 48; x += 48) {
//                graphics2D.drawImage(tile[1].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
//            }
//        }

    }

}
