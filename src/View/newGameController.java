package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import static java.lang.String.valueOf;

public class newGameController implements Initializable {

    public Stage stage;
    public TextField row = new TextField();
    public TextField col = new TextField();
    public Button start = new Button();
    public static MyViewModel myViewModel;
    MazeDisplayer mazeDisplayer = new MazeDisplayer();

    public static boolean IsEmpty = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setMyViewModel(MyViewModel myViewModel) {
        newGameController.myViewModel = myViewModel;
    }
    @FXML
    public void create() {
        mazeDisplayer.winGame = false;
        if (IsEmpty) {
            if (row == null && col == null) {
                myViewModel.createEmptyMaze(15, 15);
                if (stage != null)
                    stage.close();
            } else {
                try {
                    if (row != null && col != null) {
                        if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), "")) {
                            myViewModel.createEmptyMaze(15, 15);
                            Alert("No scales entered");
                        } else
                            myViewModel.createEmptyMaze(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()));
                        if (stage != null)
                            stage.close();
                    }
                } catch (Exception e) {
                    Alert("Please enter numbers only");
                }
            }
        } else {
            if (row == null || col == null)
            {
                System.out.println("default");
                myViewModel.createNewGame(15, 15);
            }
            else
            {
                try {
                    if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), ""))
                    {
                        myViewModel.createNewGame(15, 15);
                        Alert("No scales entered");
                    } else
                    {
                        int rows = Integer.parseInt(row.getText());
                        int columns = Integer.parseInt(col.getText());
                        if (validateMazeScales(rows, columns))
                        {
                            myViewModel.createNewGame(rows, columns);
                            //TODO: add music
                        } else {
                            myViewModel.createNewGame(15, 15);
                            Alert("You entered small scales...");
                            //TODO: add music
                        }
                    }
                    if (stage != null)
                        stage.close();
                } catch (Exception e) {
                    Alert("Please enter numbers only");
                }
            }
        }
    }

    private boolean validateMazeScales(int rows, int columns) { return rows > 2 && columns > 2;}
    private void Alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
}
