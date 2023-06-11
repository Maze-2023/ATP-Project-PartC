package View;

import ViewModel.MyViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerIconController {

    Stage stage;
    Scene scene;
    MyViewController myViewController;
    MyViewModel myViewModel;

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
        stage.setScene(scene);
        //TODO: continue with settings
    }
}
