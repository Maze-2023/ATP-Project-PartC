package View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WinController implements Initializable {
    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a Media instance with the URL or file path of your video
        String videoUrl = Objects.requireNonNull(getClass().getResource("/Images/win.mp4")).toString();
        Media media = new Media(videoUrl);

        // Create a MediaPlayer and set the Media
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set the MediaPlayer to the MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        // Start playing the video
        mediaPlayer.play();
    }
}

