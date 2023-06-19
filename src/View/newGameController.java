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

    /**
     * create the maze from definitions
     */
    @FXML
    public void create() {
        mazeDisplayer.winGame = false;
        if (IsEmpty)
        {
            if (row == null && col == null)
            {
                myViewModel.createEmptyMaze(7, 7);
                if (stage != null)
                    stage.close();
            }
            else
            {
                try {
                    if (row != null && col != null)
                    {
                        if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), ""))
                        {
                            myViewModel.createEmptyMaze(11, 11);
                            Alert("No scales entered");
                        }
                        else
                            myViewModel.createEmptyMaze(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()));
                        if (stage != null)
                            stage.close();
                    }
                }
                catch (Exception e)
                {
                    Alert("Please enter numbers only");
                }
            }
        }
        else
        {
            if (row == null || col == null)
            {
                System.out.println("default");
                myViewModel.createNewGame(11, 11);
            }
            else
            {
                try {
                    if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), ""))
                    {
                        myViewModel.createNewGame(11, 11);
                        Alert("No scales entered");
                    }
                    else
                    {
                        int rows = Integer.parseInt(row.getText());
                        int columns = Integer.parseInt(col.getText());
                        if (validateMazeScales(rows, columns))
                            myViewModel.createNewGame(rows, columns);

                        else
                        {
                            myViewModel.createNewGame(7, 7);
                            Alert("You entered small scales...");
                        }
                    }
                    if (stage != null)
                        stage.close();
                }
                catch (Exception e)
                {
                    Alert("Please enter numbers only");
                }
            }
        }
        if (stage != null)
            stage.close();
    }

    /**
     * create the maze without input from user
     */
    public void createNotNew() {
        mazeDisplayer.winGame = false;
        if (IsEmpty)
        {
            if (row == null && col == null)
            {
                myViewModel.createEmptyMaze(7, 7);
                if (stage != null)
                    stage.close();
            } else
            {
                try {
                    if (row != null && col != null)
                    {
                        if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), ""))
                            myViewModel.createEmptyMaze(7, 7);
                        else
                            myViewModel.createEmptyMaze(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()));
                        if (stage != null)
                            stage.close();
                    }
                }
                catch (Exception e){ }

            }
        }
        else
        {
            if (row == null || col == null)
                myViewModel.createNewGame(7, 7);
            else
            {
                try {
                    if (Objects.equals(valueOf(row.getText()), "") || Objects.equals(valueOf(col.getText()), ""))
                        myViewModel.createNewGame(7, 7);
                    else
                    {
                        int rows = Integer.parseInt(row.getText());
                        int columns = Integer.parseInt(col.getText());
                        if (validateMazeScales(rows, columns))
                            myViewModel.createNewGame(rows, columns);
                        else
                            myViewModel.createNewGame(7, 7);
                    }
                    if (stage != null)
                        stage.close();
                }
                catch (Exception ignored) {}
            }
        }
        if (stage != null)
            stage.close();
    }

    private boolean validateMazeScales(int rows, int columns) { return rows > 2 && columns > 2;}
    private void Alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(alertMessage);
        alert.show();
    }

    /**
     * if the algorithm is of an empty maze
     * @param b set the boolean accordingly
     */
    public void setEmptyMaze(boolean b) {
        MyViewModel.IsEmpty=b;
        myViewModel.emptyMaze=null;
    }
}
