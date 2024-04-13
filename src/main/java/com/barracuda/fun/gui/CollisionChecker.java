package com.barracuda.fun.gui;

import com.barracuda.fun.gui.entity.Entity;

public class CollisionChecker {

    private final GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        //These two variables - because we check only two corners of player for collision;
        int tileNum_1;
        int tileNum_2;

        switch (entity.direction) {
            case "up":
                //predict where player will be when he moves:
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum_1 = gamePanel.tileManager.mapTile[entityLeftCol][entityTopRow];
                tileNum_2 = gamePanel.tileManager.mapTile[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum_1].collision == true || gamePanel.tileManager.tile[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                //predict where player will be when he moves:
                entityBottomRow = (entityBottomWorldY - entity.speed) / gamePanel.tileSize;
                tileNum_1 = gamePanel.tileManager.mapTile[entityLeftCol][entityBottomRow];
                tileNum_2 = gamePanel.tileManager.mapTile[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum_1].collision == true || gamePanel.tileManager.tile[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                //predict where player will be when he moves:
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum_1 = gamePanel.tileManager.mapTile[entityLeftCol][entityTopRow];
                tileNum_2 = gamePanel.tileManager.mapTile[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum_1].collision == true || gamePanel.tileManager.tile[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                //predict where player will be when he moves:
                entityRightCol = (entityRightWorldX - entity.speed) / gamePanel.tileSize;
                tileNum_1 = gamePanel.tileManager.mapTile[entityRightCol][entityTopRow];
                tileNum_2 = gamePanel.tileManager.mapTile[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum_1].collision == true || gamePanel.tileManager.tile[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkItem(Entity entity, boolean isPlayer) {
        int index = 999;
        for (int i = 0; i < gamePanel.items.length; i++) {
            if (gamePanel.items[i] != null) {
                // Get entities solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get item's solid area position
                gamePanel.items[i].solidArea.x = gamePanel.items[i].worldX + gamePanel.items[i].solidArea.x;
                gamePanel.items[i].solidArea.y = gamePanel.items[i].worldY + gamePanel.items[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.items[i].solidArea)) {
                            if (gamePanel.items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.items[i].solidArea)) {
                            if (gamePanel.items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.items[i].solidArea)) {
                            if (gamePanel.items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.items[i].solidArea)) {
                            if (gamePanel.items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                }
                //Reset solid area coordinates:
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.items[i].solidArea.x = gamePanel.items[i].solidAreaDefaultX;
                gamePanel.items[i].solidArea.y = gamePanel.items[i].solidAreaDefaultY;
            }
        }
        return index;
    }

}
