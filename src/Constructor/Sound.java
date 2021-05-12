package Constructor;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;

public class Sound {

    private static MediaPlayer mediaPlayer;
    private static Media media;
    public static void playSound(String name){
        media = new Media(new File(System.getProperty("user.dir") + name).toURI().toString());
        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setAutoPlay(true);
    }
}
