package View;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class newGameController implements Initializable {

    public Stage stage;
    public TextField row = new TextField();
    public TextField col = new TextField();
    public Button start = new Button();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

}
