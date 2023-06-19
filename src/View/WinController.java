package View;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WinController implements Initializable {
    @FXML
    private MediaView mediaView;
    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    Scene scene;
    private MediaPlayer mediaPlayer;

    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    private MyViewModel myViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a Media instance with the URL or file path of your video
        File videoUrl = new File("resources/music/win.mp4");
        Media media = new Media(videoUrl.toURI().toString());

        // Create a MediaPlayer and set the Media
        mediaPlayer = new MediaPlayer(media);

        // Set the MediaPlayer to the MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        // Start playing the video
        mediaPlayer.play();
    }

    public void exit(ActionEvent actionEvent) {
       myViewModel.exit();
       stage.close();
    }

    public void start(ActionEvent actionEvent) throws IOException {
        mediaPlayer.stop();
        StartAgain startAgain = new StartAgain();
        startAgain.start(stage);
    }
}

