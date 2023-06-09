package View;

import Model.MyModel;
import Model.MyModelGenerator;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Run game from here
 */
public class HelloApplication extends Application {
    public Scene playerIconScene;
    public Scene gameScene;
    public Scene welcomeScene;
    public static MediaPlayer BackGroundPlayer;
    MyViewModel myViewModel;
    public static final Logger logger = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {

        //initialize
        MyModel model= MyModelGenerator.generateMyModel();
        myViewModel = new MyViewModel(model);
        model.addToMe(myViewModel);

        //add music
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

        //begin pages and add from top to bottom
        stage.setTitle("Welcome");
        FXMLLoader welcomeFXML = new FXMLLoader(getClass().getResource("Welcome.fxml"));
        Parent welcome = welcomeFXML.load();
        welcomeScene = new Scene(welcome,900,600);
        welcomeScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        WelcomeController welcomeController = welcomeFXML.getController();

        FXMLLoader playerFXML = new FXMLLoader(getClass().getResource("PlayerIcon.fxml"));
        Parent player = playerFXML.load();
        PlayerIconController playerController = playerFXML.getController();
        playerIconScene = new Scene(player,900,600);
        playerController.setStage(stage);

        FXMLLoader gameFXML = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent game = gameFXML.load();
        MyViewController gameController = gameFXML.getController();
        gameScene = new Scene(game,900,600);

        //connect all the scenes and stages
        welcomeController.setStage(stage);
        welcomeController.setScene(playerIconScene);

        playerController.setStage(welcomeController.getStage());
        playerController.setScene(gameScene);

        playerController.setMyViewModel(myViewModel);
        gameController.changeScreenSize(gameScene);
        playerController.setMyViewController(gameController);

        //add listener
        myViewModel.addToMe(gameController);

        //update stage
        gameController.setStage(welcomeController.getStage());
        gameController.setScene(gameScene);


        //double check on exit
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

        //run from welcome scene
        stage.setScene(welcomeScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}