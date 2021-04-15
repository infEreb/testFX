package Constructor;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;

public class Sound {

    private MediaPlayer mediaPlayer;
    public Sound(String name){

        Media media = new Media(new File(System.getProperty("user.dir") + name).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

    }
    public void playSound(){
        mediaPlayer.play();
    }
}
