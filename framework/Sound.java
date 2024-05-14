package framework;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        initializeSoundURLs();
    }

    private void initializeSoundURLs() {
        soundURL[0] = getClass().getResource("/sound/piano.wav");
        soundURL[1] = getClass().getResource("/sound/eating.wav");
        soundURL[2] = getClass().getResource("/sound/applause.wav");
        soundURL[3] = getClass().getResource("/sound/death.wav");
    }

    public void getFiles(int i) {
        try {
            URL url = soundURL[i];
            if (url == null) {
                System.out.println("Resource not found: " + i);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Audio loaded and clip opened: " + i);
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace to understand what went wrong
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
