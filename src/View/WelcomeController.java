package View;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    public Stage stage;
    public Scene scene;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void play()
    {
        stage.setScene(scene);
    }
}
