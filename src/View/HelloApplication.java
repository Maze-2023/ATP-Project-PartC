package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Maze Game!");
        stage.setScene(scene);

        String songPath = "resources/Music/ohcelia.mp3";
        Media song = new Media(new File(songPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(song);

        // Play the song after the stage is shown
        //stage.setOnShown(event -> mediaPlayer.play());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}