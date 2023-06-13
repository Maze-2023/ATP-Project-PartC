package View;

import ViewModel.MyViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {
    @FXML
    public View.MazeDisplayer MazeDisplayer;

    MyViewModel myViewModel;
    newGameController newGameController;

    Scene scene;
    Stage stage;

    @FXML
    Label position;

    @FXML
    public Pane pane;
    @Override
    public void setScene(Scene scene) {
        this.scene=scene;
    }

    @FXML
    public void setVM(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void setStage(Stage primaryStage) {
    this.stage=primaryStage;
    }

    @Override
    public void changeScreenSize(Scene scene) {
        MazeDisplayer.widthProperty().bind(pane.widthProperty());
        MazeDisplayer.heightProperty().bind(pane.heightProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            MazeDisplayer.widthProperty().bind(pane.widthProperty());
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            MazeDisplayer.heightProperty().bind(pane.heightProperty());
        });
    }
    @Override
    public void setPlayerIcon(String s) throws Exception {
        MazeDisplayer.setIcon(s);
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
        String message=(String)arg;
        if (o == myViewModel) {
            if (message.equals("generated"))
            {
                MazeDisplayer.setMyViewModel(myViewModel);
                MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame());
                position.textProperty().bind(Bindings.concat(myViewModel.stringPlayerR+","+myViewModel.stringPlayerC));

            }
            if(arg.equals("playerMove"))
            {
                MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.draw();
                position.textProperty().bind(Bindings.concat(myViewModel.stringPlayerR+","+myViewModel.stringPlayerC));

            }
            if ((arg.equals("solved")))
            {
//                MazeDisplayer.setSolutionObj(MYVM.getSolution());
//                MazeDisplayer.drawSolution();
//                position.textProperty().bind(Bindings.concat(myViewModel.stringPlayerR+","+myViewModel.stringPlayerC));

            }
            if ((arg.equals("loaded")))
            {
                MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame());
            }
            if (message.equals("empty"))
            {
                MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame());
                position.textProperty().bind(Bindings.concat(myViewModel.stringPlayerR+","+myViewModel.stringPlayerC));

            }
        }
    }

    public void newGame(javafx.event.ActionEvent actionEvent)
    {
        try {
            Stage stage = new Stage();
            stage.setTitle("New Game");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newGame.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 500, 400);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
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
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
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

}