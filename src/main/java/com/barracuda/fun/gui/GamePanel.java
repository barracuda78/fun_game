package com.barracuda.fun.gui;

import static com.barracuda.fun.gui.constants.ScreenSettings.FPS;

import com.barracuda.fun.gui.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GamePanel implements Runnable {

    private final KeyHandler keyHandler;
    private final ItemPlacerServiceImpl itemPlacerService;
    private final NpcPlacerServiceImpl npcPlacerService;
    private final Player player ;
    private final DrawServiceImpl drawService;
    private final GameThreadServiceImpl gameThreadService;
//    public Thread gameThread;

    // GAME STATE:
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;
    public int gameState = playState;

    public void startGameThread() {
        gameThreadService.startGameThread(this);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThreadService.gameThreadRunning()) {
            checkGameInterrupted();
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update(player);
                drawService.paint(); //here we call paintComponent(Graphics graphics) method
                delta--;
            }
        }
    }

    public void update(Player player) {
        if (!keyHandler.paused) {
            player.update(itemPlacerService.getItems(), npcPlacerService.getNpcs());
            for (int i = 0; i < npcPlacerService.getNpcs().length; i++) {
                if(npcPlacerService.getNpcs()[i] != null) {
                    npcPlacerService.getNpcs()[i].update(player, itemPlacerService.getItems());
                }
            }
        }
    }

    public void checkGameInterrupted() {
        if (keyHandler.gameInterrupted) {
            gameThreadService.interruptGameThread();
            if (gameThreadService.gameThreadInterrupted()) {
                System.exit(0);
            }
        }
    }

}
