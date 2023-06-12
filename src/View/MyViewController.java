package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MyViewController implements IView{
    MazeDisplayer mazeDisplayer;
    newGameController newGameController;
    @Override
    public void setScene(Scene scene) {

    }

    @Override
    public void setVM(MyViewModel myViewModel) {

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

    }

    public void setNewGame(newGameController newGame)
    {
        newGameController=newGame;
    }
}