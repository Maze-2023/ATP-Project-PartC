package View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WinController implements Initializable {
    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a Media instance with the URL or file path of your video
        File videoUrl = new File("music/win.mp4");
        Media media = new Media(videoUrl.toURI().toString());

        // Create a MediaPlayer and set the Media
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set the MediaPlayer to the MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        // Start playing the video
        mediaPlayer.play();
    }
}

