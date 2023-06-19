package View;

import ViewModel.MyViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * interface for classes that set the game object on display
 */
public interface IView {
    void setScene(Scene scene);
    void setVM(MyViewModel myViewModel);
    void setStage(Stage primaryStage);
    //change the size of the game object
    void changeScreenSize(Scene scene);
    //set the player icon to the game
    void setPlayerIcon(String s) throws Exception;
    void exit();
}
