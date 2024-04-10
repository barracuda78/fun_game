package com.barracuda.fun.gui.tile;

import com.barracuda.fun.gui.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileManager {

    GamePanel gamePanel;

    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_water.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/graphics/tiles/tile_brick.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        //brick:
        for (int x = 0; x < 16*48; x+=48) {
            graphics2D.drawImage(tile[2].image, x, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        }
        for (int y = 48; y < 11*48; y+=48) {
            graphics2D.drawImage(tile[2].image, 0, y, gamePanel.tileSize, gamePanel.tileSize, null);
        }
        for (int y = 48; y < 11*48; y+=48) {
            graphics2D.drawImage(tile[2].image, 15*48, y, gamePanel.tileSize, gamePanel.tileSize, null);
        }
        for (int x = 0; x < 16*48; x+=48) {
            graphics2D.drawImage(tile[2].image, x, 11*48, gamePanel.tileSize, gamePanel.tileSize, null);
        }

        //grass:
        for (int y = 48; y < 11*48; y+=48) {
            for (int x = 48; x < 15 * 48; x += 48) {
                graphics2D.drawImage(tile[0].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

        //water:
        for (int y = 48*5; y < 8*48; y+=48) {
            for (int x = 48 * 4; x < 12 * 48; x += 48) {
                graphics2D.drawImage(tile[1].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

    }

}
