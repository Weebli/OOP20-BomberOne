package bomberOne.tools.audio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class AudioHandler {

    private static final Map<GameAudio, Media> CACHE;
    private static MediaPlayer player;

    static {
        CACHE = new EnumMap<>(GameAudio.class);
        Arrays.stream(GameAudio.values()).forEach(values -> {
            try {
                final Media audio = new Media(ClassLoader.getSystemResource(values.getMediaPath()).toURI().toString());
                CACHE.put(values, audio);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    private AudioHandler() {

    }

    public static synchronized void start(final GameAudio type) {
        player = new MediaPlayer(CACHE.get(type));
        player.setVolume(type.getVolume());
        player.play();
    }

    public static synchronized void stop() {
        player.stop();
    }
}
