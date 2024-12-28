package com.barracuda.fun.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameThreadServiceImpl {

  private Thread gameThread;

  public void startGameThread(GamePanel gamePanel) {
    initializeGameThread(gamePanel);
    gameThread.start();
  }

  public void stopGameThread() {
    gameThread = null;
  }

  public boolean gameThreadRunning() {
    return gameThread != null;
  }

  public void interruptGameThread() {
    gameThread.interrupt();
  }

  public boolean gameThreadInterrupted() {
    return gameThread.isInterrupted();
  }

  private void initializeGameThread(GamePanel gamePanel) {
    gameThread = new Thread(gamePanel);
  }

}
