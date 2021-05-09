package bomberOne.tools.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;

import javafx.scene.media.Media;

public enum GameAudio {

    /**
     * 
     */
    CLASSIC("classic.mp3", 0.4),

    /**
     * 
     */
    HOME("home.wav", 0.4);

    private static final String RES_PATH = "audio/";

    private String fileName;
    private double volume;

    GameAudio(final String fileName, final double volume) {
        this.fileName = fileName;
        this.volume = volume;
    }

    public double getVolume() {
        return this.volume;
    }
    public String getMediaPath() {
        return RES_PATH + this.fileName;
    }
}
