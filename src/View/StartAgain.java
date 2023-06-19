package View;

import Model.MyModel;
import Model.MyModelGenerator;
import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * if the user wants to play a new game
 * does the same as hello app
 */
public class StartAgain {
    public Scene playerIconScene;
    public Scene gameScene;
    public Scene welcomeScene;
    public static MediaPlayer BackGroundPlayer;
    MyViewModel myViewModel;

    //same as hello function without the welcome page
    public void start(Stage stage) throws IOException {
        MyModel model= MyModelGenerator.generateMyModel();
        myViewModel = new MyViewModel(model);
        model.addToMe(myViewModel);

        File Wins = new File("resources/music/open.mp3");
        Media Song = new Media((Wins.toURI().toString()));
        BackGroundPlayer = new MediaPlayer(Song);
        BackGroundPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                BackGroundPlayer.seek(Duration.ZERO);
                BackGroundPlayer.play();
            }
        });
        BackGroundPlayer.play();

        FXMLLoader playerFXML = new FXMLLoader(getClass().getResource("PlayerIcon.fxml"));
        Parent player = playerFXML.load();
        PlayerIconController playerController = playerFXML.getController();
        playerIconScene = new Scene(player,900,600);
        playerController.setStage(stage);


        FXMLLoader gameFXML = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent game = gameFXML.load();
        MyViewController gameController = gameFXML.getController();
        gameScene = new Scene(game,900,600);

        playerController.setStage(stage);
        playerController.setScene(gameScene);

        playerController.setMyViewModel(myViewModel);
        gameController.changeScreenSize(gameScene);
        playerController.setMyViewController(gameController);

        myViewModel.addToMe(gameController);

        gameController.setStage(stage);
        gameController.setScene(gameScene);

        stage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent immediate window closing

            // Display a confirmation dialog
            int confirmed = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to quit?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                // Call your exit function here or close the stage
                // Exit function example: exitFunction();
                stage.close();
                model.exit();
            }
        });

        stage.setScene(playerIconScene);
        stage.show();
    }
}
