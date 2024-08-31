package com.barracuda.fun.gui.entity;

import com.barracuda.fun.enums.MovementDirection;
import com.barracuda.fun.gui.CollisionChecker;
import com.barracuda.fun.gui.PlayerCoordinatesService;
import com.barracuda.fun.gui.TileCoordinatesService;
import com.barracuda.fun.gui.entity.draw_handler.DrawEntityDirectionHandlerRegistry;
import com.barracuda.fun.gui.enums.Direction;
import jakarta.annotation.PostConstruct;
import java.awt.Point;
import java.awt.image.BufferedImage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DogNpc extends Entity {

    private final PlayerCoordinatesService playerCoordinatesService;

    private final TileCoordinatesService tileCoordinatesService;

    public DogNpc(CollisionChecker collisionChecker,
        DrawEntityDirectionHandlerRegistry drawEntityDirectionHandlerRegistry,
        Sprites sprites,
        PlayerCoordinatesService playerCoordinatesService,
        TileCoordinatesService tileCoordinatesService
    ) {
        super(collisionChecker, drawEntityDirectionHandlerRegistry, sprites);
        this.playerCoordinatesService = playerCoordinatesService;
        this.tileCoordinatesService = tileCoordinatesService;
    }

    @PostConstruct
    public void init() {
        direction = "down";
        speed = 1;
        loadDogNPCImage();
    }

    public void loadDogNPCImage() {
        BufferedImage up_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        BufferedImage up_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        BufferedImage down_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        BufferedImage down_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        BufferedImage left_1 = loadScaledImage("/graphics/npc/dog_up_01.png");
        BufferedImage left_2 = loadScaledImage("/graphics/npc/dog_up_02.png");
        BufferedImage right_1 = loadScaledImage("/graphics/npc/dog_down_01.png");
        BufferedImage right_2 = loadScaledImage("/graphics/npc/dog_down_02.png");
        sprites.addSprite(up_1, MovementDirection.UP, 1); //TODO do not duplicate code in DogNpc and Player class!
        sprites.addSprite(up_2, MovementDirection.UP, 2);
        sprites.addSprite(down_1, MovementDirection.UP, 1);
        sprites.addSprite(down_2, MovementDirection.UP, 2);
        sprites.addSprite(left_1, MovementDirection.UP, 1);
        sprites.addSprite(left_2, MovementDirection.UP, 2);
        sprites.addSprite(right_1, MovementDirection.UP, 1);
        sprites.addSprite(right_2, MovementDirection.UP, 2);
    }

    //AI method of characters behavior:
    @Override
    public void setAction() {
        actionLockCounter++;

        Point playerCoordinates = playerCoordinatesService.getPlayerCoordinates();
        Point thisNpcCoordinates = this.getCoordinates();

        if(actionLockCounter == 120) {
            final boolean rightTileSolid = tileCoordinatesService.isNearestTileSolid(thisNpcCoordinates, Direction.RIGHT);
            final boolean upTileSolid = tileCoordinatesService.isNearestTileSolid(thisNpcCoordinates, Direction.UP);
            final boolean downTileSolid = tileCoordinatesService.isNearestTileSolid(thisNpcCoordinates, Direction.DOWN);
            final boolean leftTileSolid = tileCoordinatesService.isNearestTileSolid(thisNpcCoordinates, Direction.LEFT);
            int xDifference = playerCoordinates.x - thisNpcCoordinates.x;
            int yDifference = playerCoordinates.y - thisNpcCoordinates.y;

            int xDiffPositive = xDifference < 0 ? xDifference * -1 : xDifference;
            int yDiffPositive = yDifference < 0 ? yDifference * -1 : yDifference;

            // Player is right up from the dog:
            if (playerCoordinates.x >= thisNpcCoordinates.x && playerCoordinates.y <= thisNpcCoordinates.y) {
                if (xDiffPositive > yDiffPositive) {
                        if(! rightTileSolid) {
                            direction = "right";
                        } else if (! upTileSolid) {
                            direction = "up";
                        } else if (! downTileSolid) {
                            direction = "down";
                        } else if (! leftTileSolid) {
                            direction = "left";
                        }
                } else {
                        if (! upTileSolid) {
                            direction = "up";
                        } else if (! rightTileSolid) {
                            direction = "right";
                        } else if (! downTileSolid) {
                            direction = "down";
                        } else if (! leftTileSolid) {
                            direction = "left";
                        }
                    }
            }
            // Player is left up from the dog:
            else if (playerCoordinates.x < thisNpcCoordinates.x && playerCoordinates.y <= thisNpcCoordinates.y) {
                if (xDiffPositive > yDiffPositive) {
                    if(! leftTileSolid) {
                        direction = "left";
                    } else if (! upTileSolid) {
                        direction = "up";
                    } else if (! downTileSolid) {
                        direction = "down";
                    } else if (! rightTileSolid) {
                        direction = "right";
                    }
                } else {
                    if (! upTileSolid) {
                        direction = "up";
                    } else if(! leftTileSolid) {
                        direction = "left";
                    }  else if (! downTileSolid) {
                        direction = "down";
                    } else if (! rightTileSolid) {
                        direction = "right";
                    }
                }

            }

            // Player is right down from the dog:
            else if (playerCoordinates.x >= thisNpcCoordinates.x && playerCoordinates.y > thisNpcCoordinates.y) {
                if (xDiffPositive > yDiffPositive) {
                    if (! rightTileSolid) {
                        direction = "right";
                    } else if (! downTileSolid) {
                        direction = "down";
                    } else if (! upTileSolid) {
                        direction = "up";
                    }  else if (! leftTileSolid) {
                        direction = "left";
                    }
                } else {
                    if (! downTileSolid) {
                        direction = "down";
                    } else if (! rightTileSolid) {
                        direction = "right";
                    }  else if (! upTileSolid) {
                        direction = "up";
                    }  else if (! leftTileSolid) {
                        direction = "left";
                    }
                }
            }

            // Player is left down from the dog:
            else if (playerCoordinates.x < thisNpcCoordinates.x && playerCoordinates.y > thisNpcCoordinates.y) {
                if (xDiffPositive > yDiffPositive) {
                    if (! leftTileSolid) {
                        direction = "left";
                    } else if (! downTileSolid) {
                        direction = "down";
                    } else if (! upTileSolid) {
                        direction = "up";
                    }  else if (! rightTileSolid) {
                        direction = "right";
                    }
                } else {
                    if (! downTileSolid) {
                        direction = "down";
                    } else if (! leftTileSolid) {
                        direction = "left";
                    }  else if (! upTileSolid) {
                        direction = "up";
                    }  else if (! rightTileSolid) {
                        direction = "right";
                    }
                }
            }
            actionLockCounter = 0;
        }


    }

}
