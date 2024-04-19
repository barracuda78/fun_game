package com.barracuda.fun.gui;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Sound {

    Clip clip;

    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/graphics/sound/fun_game.wav");
        soundURL[1] = getClass().getResource("/graphics/sound/sonar.wav");
        soundURL[2] = getClass().getResource("/graphics/sound/din-don.wav");
        soundURL[3] = getClass().getResource("/graphics/sound/don-din.wav");
        soundURL[4] = getClass().getResource("/graphics/sound/fanfare.wav");
    }
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(" setFile method in Sound class threw exception");
        }

    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
