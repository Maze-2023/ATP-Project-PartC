package View;

import ViewModel.MyViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface IView {
    void setScene(Scene scene);

    void setVM(MyViewModel myViewModel);

    void setStage(Stage primaryStage);

    void changeScreenSize(Scene scene);

    void setPlayerIcon(String s) throws Exception;

    void exit();
}
