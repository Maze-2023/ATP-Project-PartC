package View;

import Server.Configurations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
public class PropertiesController implements Initializable {
    Stage stage;
    newGameController newGameController;
    public ChoiceBox<String> algorithmSearch;
    public ChoiceBox<String> mazeType;

    //get the value to be shown on the choice box by properties
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Configurations config = Configurations.getInstance();

        mazeType.getItems().addAll("EmptyMazeGenerator","SimpleMazeGenerator","MyMazeGenerator");
        algorithmSearch.getItems().addAll("BreadthFirstSearch","DepthFirstSearch", "BestFirstSearch");
        try{

            String algo = config.properties.getProperty("mazeSearchingAlgorithm");
            String type = config.properties.getProperty("mazeGeneratingAlgorithm");

            if(algo.equals("BestFirstSearch")){
                algorithmSearch.setValue("BestFirstSearch");}
            else if(algo.equals("DepthFirstSearch")){
                algorithmSearch.setValue("DepthFirstSearch");}
            else if(algo.equals("BreadthFirstSearch")){
                algorithmSearch.setValue("BreadthFirstSearch");}
            if(type.equals("MyMazeGenerator")){
                mazeType.setValue("MyMazeGenerator");}
            else if(type.equals("SimpleMazeGenerator")){
                mazeType.setValue("SimpleMazeGenerator");}
            else if(type.equals("EmptyMazeGenerator")){
                mazeType.setValue("EmptyMazeGenerator");}
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {this.stage = stage;}

    public void setNewGame(newGameController newGame){this.newGameController=newGame;}
    @FXML
    public void setConfig(){
        Configurations config = Configurations.getInstance();
        config.properties.setProperty("mazeGeneratingAlgorithm",mazeType.getValue());
        config.properties.setProperty("mazeSearchingAlgorithm",algorithmSearch.getValue());
        if (algorithmSearch.getValue().equals("EmptyMazeGenerator"))
            View.newGameController.IsEmpty =true;
        else
            newGameController.setEmptyMaze(false);

        newGameController.createNotNew();
        stage.close();
    }
}
