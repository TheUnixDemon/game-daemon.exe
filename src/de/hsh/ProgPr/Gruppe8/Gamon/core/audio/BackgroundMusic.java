package de.hsh.ProgPr.Gruppe8.Gamon.core.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Simple background music player.
 * Plays a WAV file in an infinite loop.
 * No pause / resume support by design.
 */
public class BackgroundMusic {
    private String basePath = "./de/hsh/ProgPr/Gruppe8/Gamon/srv/audio/"; // CHANGEME
    private String filename = "background.wav";
    private String wavPath = basePath + filename;
    private Clip clip;

    /**
     * Creates and immediately starts looping background music
     */
    public BackgroundMusic() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(basePath + filename));

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException |
                 IOException |
                 LineUnavailableException e) {

            System.err.println("Failed to play background music: " + wavPath);
            e.printStackTrace();
        }
    }

    /**
     * Stops the music completely and releases resources.
     * (No pause functionality on purpose)
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
