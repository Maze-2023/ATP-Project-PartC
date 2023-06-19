package View;

import ViewModel.MyViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class PlayerIconController {
    Stage stage;
    Scene scene;
    MyViewController myViewController;
    MyViewModel myViewModel;
    public static MediaPlayer BackGroundPlayer;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setMyViewController(MyViewController myViewController) {
        this.myViewController = myViewController;
        myViewController.setVM(myViewModel);
    }

    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    public void setStage(Stage primaryStage)
    {
        stage = primaryStage;
    }

    public void handleMike() throws Exception {
        myViewController.setPlayerIcon("Mike");
        iconChosen();
    }
    public void handleSully() throws Exception {
        myViewController.setPlayerIcon("Sully");
        iconChosen();
    }
    public void handleBoo() throws Exception {
        myViewController.setPlayerIcon("Boo");
        iconChosen();
    }
    public void handleRoz() throws Exception {
        myViewController.setPlayerIcon("Roz");
        iconChosen();
    }
    public void handleCelia() throws Exception {
        myViewController.setPlayerIcon("Celia");
        iconChosen();
    }

    public void iconChosen()
    {
        HelloApplication.BackGroundPlayer.stop();
        File open = new File("music/openSong.mp3");
        Media Song = new Media((open.toURI().toString()));
        BackGroundPlayer = new MediaPlayer(Song);
        BackGroundPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                BackGroundPlayer.seek(Duration.ZERO);
                BackGroundPlayer.play();
            }
        });
        BackGroundPlayer.play();
        stage.setScene(scene);
        //update bindings
        newGameController.myViewModel=myViewModel;
        newGameController createGame = new newGameController();
        myViewController.setNewGame(createGame);
        //begin
        createGame.create();
    }
}
