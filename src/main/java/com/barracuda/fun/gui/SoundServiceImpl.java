package com.barracuda.fun.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoundServiceImpl {

  private final Sound music;

  private final Sound soundEffect;

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
