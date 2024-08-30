package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.TILE_SIZE;

import com.barracuda.fun.gui.entity.Entity;
import com.barracuda.fun.gui.entity.Player;
import com.barracuda.fun.gui.item.Item;
import com.barracuda.fun.gui.tile.TileManager;
import org.springframework.stereotype.Component;

@Component
public class CollisionChecker {

    private final TileManager tileManager;

    public CollisionChecker(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getCoordinates().x + entity.solidArea.x;
        int entityRightWorldX = entity.getCoordinates().x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getCoordinates().y + entity.solidArea.y;
        int entityBottomWorldY = entity.getCoordinates().y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / TILE_SIZE;
        int entityRightCol = entityRightWorldX / TILE_SIZE;
        int entityTopRow = entityTopWorldY / TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TILE_SIZE;

        //These two variables - because we check only two corners of player for collision;
        int tileNum_1;
        int tileNum_2;

        switch (entity.direction) {
            case "up":
                //predict where player will be when he moves:
                entityTopRow = (entityTopWorldY - entity.speed) / TILE_SIZE;
                tileNum_1 = tileManager.mapTileArray[entityLeftCol][entityTopRow];
                tileNum_2 = tileManager.mapTileArray[entityRightCol][entityTopRow];
                if (tileManager.tileArray[tileNum_1].collision == true || tileManager.tileArray[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                //predict where player will be when he moves:
                entityBottomRow = (entityBottomWorldY + entity.speed) / TILE_SIZE;
                tileNum_1 = tileManager.mapTileArray[entityLeftCol][entityBottomRow];
                tileNum_2 = tileManager.mapTileArray[entityRightCol][entityBottomRow];
                if (tileManager.tileArray[tileNum_1].collision == true || tileManager.tileArray[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                //predict where player will be when he moves:
                entityLeftCol = (entityLeftWorldX - entity.speed) / TILE_SIZE;
                tileNum_1 = tileManager.mapTileArray[entityLeftCol][entityTopRow];
                tileNum_2 = tileManager.mapTileArray[entityLeftCol][entityBottomRow];
                if (tileManager.tileArray[tileNum_1].collision == true || tileManager.tileArray[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                //predict where player will be when he moves:
                entityRightCol = (entityRightWorldX + entity.speed) / TILE_SIZE;
                tileNum_1 = tileManager.mapTileArray[entityRightCol][entityTopRow];
                tileNum_2 = tileManager.mapTileArray[entityRightCol][entityBottomRow];
                if (tileManager.tileArray[tileNum_1].collision == true || tileManager.tileArray[tileNum_2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkItem(Entity entity, boolean isPlayer, Item[] items) {
        int index = 999;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                // Get entities solid area position
                entity.solidArea.x = entity.getCoordinates().x + entity.solidArea.x;
                entity.solidArea.y = entity.getCoordinates().y + entity.solidArea.y;
                // Get item's solid area position
                items[i].solidArea.x = items[i].worldX + items[i].solidArea.x;
                items[i].solidArea.y = items[i].worldY + items[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(items[i].solidArea)) {
                            if (items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(items[i].solidArea)) {
                            if (items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(items[i].solidArea)) {
                            if (items[i].supportsCollision) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(items[i].solidArea)) {
                            if (items[i].supportsCollision) {
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
                items[i].solidArea.x = items[i].solidAreaDefaultX;
                items[i].solidArea.y = items[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //TODO: mostly copied from above checkItem() method. Refactor
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entities solid area position
                entity.solidArea.x = entity.getCoordinates().x + entity.solidArea.x;
                entity.solidArea.y = entity.getCoordinates().y + entity.solidArea.y;
                // Get item's solid area position
                target[i].solidArea.x = target[i].getCoordinates().x + target[i].solidArea.x;
                target[i].solidArea.y = target[i].getCoordinates().y + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                //Reset solid area coordinates:
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity, Player player) {
        // Get entities solid area position
        entity.solidArea.x = entity.getCoordinates().x + entity.solidArea.x;
        entity.solidArea.y = entity.getCoordinates().y + entity.solidArea.y;
        // Get item's solid area position
        player.solidArea.x = player.getCoordinates().x + player.solidArea.x;
        player.solidArea.y = player.getCoordinates().y + player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
        }
        //Reset solid area coordinates:
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
    }

}
