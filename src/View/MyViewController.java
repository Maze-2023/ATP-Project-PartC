package View;

import ViewModel.MyViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javax.swing.*;
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

        scene.setOnScroll(event -> {
            double scaleFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
            pane.setScaleX(pane.getScaleX() * scaleFactor);
            pane.setScaleY(pane.getScaleY() * scaleFactor);
            event.consume();
        });

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            MazeDisplayer.setWidth(newValue.doubleValue() / pane.getScaleX());
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            MazeDisplayer.setHeight(newValue.doubleValue() / pane.getScaleY());
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
            int row = Integer.parseInt(myViewModel.stringPlayerR.getValue())+1;
            int col = Integer.parseInt(myViewModel.stringPlayerC.getValue())+1;
            if (message.equals("generated"))
            {
                View.MazeDisplayer.setMyViewModel(myViewModel);
                View.MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                View.MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame(), stage);
                position.textProperty().bind(Bindings.concat(col+","+row));

            }
            if(arg.equals("playerMove"))
            {
                View.MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                View.MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.draw(stage);
                position.textProperty().bind(Bindings.concat(col+","+row));

            }
            if ((arg.equals("solved")))
            {
                MazeDisplayer.setSolution(myViewModel.getSolution());
                MazeDisplayer.drawSolution(stage);
                position.textProperty().bind(Bindings.concat(col+","+row));

            }
            if ((arg.equals("loaded")))
            {
                View.MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                View.MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame(), stage);
            }
            if (message.equals("empty"))
            {
                View.MazeDisplayer.setPlayerC(myViewModel.getPlayerC());
                View.MazeDisplayer.setPlayerR(myViewModel.getPlayerR());
                MazeDisplayer.drawMaze(myViewModel.getFrame(), stage);
                position.textProperty().bind(Bindings.concat(col+","+row));
            }
        }
    }

    public void help(javafx.event.ActionEvent actionEvent)
    {
        try {
            Stage stage = new Stage();
            stage.setTitle("Help");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("help.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 748, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void newGame(javafx.event.ActionEvent actionEvent)
    {
        try {
            Stage stage = new Stage();
            stage.setTitle("New Game");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newGame.fxml"));
            Parent root = fxmlLoader.load();

            newGameController=fxmlLoader.getController();
            newGameController.setStage(stage);

            Scene scene = new Scene(root, 748, 400);
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

    public void Exit() throws IOException {
        int confirmed = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to quit?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            // Call your exit function here or close the stage
            // Exit function example: exitFunction();
            exit();
        }
        System.exit(0);
    }

    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.movePlayer(keyEvent.getCode());
        keyEvent.consume();
    }

    public void onclick(MouseEvent mouseEvent) {
            MazeDisplayer.requestFocus();
    }

    public void solve(ActionEvent actionEvent) {
        myViewModel.solve();
    }
}