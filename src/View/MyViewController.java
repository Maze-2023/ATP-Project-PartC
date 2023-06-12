package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {
    MazeDisplayer mazeDisplayer;
    MyViewModel myViewModel;
    newGameController newGameController;

    @FXML
    public Pane pane;
    @Override
    public void setScene(Scene scene) {

    }

    @FXML
    public void setVM(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void setStage(Stage primaryStage) {

    }

    @Override
    public void changeScreenSize(Scene scene) {

    }

    @Override
    public void setPlayerIcon(String s) throws Exception {
        mazeDisplayer.setIcon(s);
    }

    @Override
    public void exit() {
        myViewModel.exit();
    }

    public void setNewGame(newGameController newGame)
    {
        newGameController=newGame;
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    public void newGame(javafx.event.ActionEvent actionEvent)
    {
        try {
            Stage stage = new Stage();
            stage.setTitle("New Game");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newGame.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 500, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void About(javafx.event.ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 748, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Properties(javafx.event.ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Properties.fxml"));
            Parent root = fxmlLoader.load();

            //set new properties
            PropertiesController propController = fxmlLoader.getController();
            propController.setStage(stage);
            propController.setNewGame(newGameController);

            Scene scene = new Scene(root, 748, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Exit(javafx.event.ActionEvent event) throws IOException {
        exit();
        System.exit(0);

    }

    //TODO: check if works
    public void setResizeEvent(Scene scene) {
        mazeDisplayer.widthProperty().bind(pane.widthProperty());
        mazeDisplayer.heightProperty().bind(pane.heightProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.widthProperty().bind(pane.widthProperty());
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.heightProperty().bind(pane.heightProperty());
        });
    }
}