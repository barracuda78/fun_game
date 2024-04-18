package com.barracuda.fun.gui;

import org.springframework.stereotype.Service;

@Service
public class SoundServiceImpl { //todo: check if it could be a singleton

  Sound music = new Sound(); //prototype?
  Sound soundEffect = new Sound(); //prototype?

  public void playMusic(int i) {
    music.setFile(i);
    music.play();
    music.loop();
  }

  public void stopMusic() {
    music.stop();
  }

  public void playSoundEffect(int i) {
    soundEffect.setFile(i);
    soundEffect.play();
  }

}
