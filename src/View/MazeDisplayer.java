package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private static int playerR=0;
    private static int playerC=0;

    static MyViewModel myViewModel;

    public static MediaPlayer mediaPlayer;
    public boolean winGame=false;
    Image playerIcon;
    StringProperty MikeImg = new SimpleStringProperty("resources/Images/Mike.png");
    StringProperty SullyImg = new SimpleStringProperty("resources/Images/Sully.png");
    StringProperty BooImg = new SimpleStringProperty("resources/Images/boo.png");
    StringProperty RozImg = new SimpleStringProperty("resources/Images/roz.png");
    StringProperty CeliaImg = new SimpleStringProperty("resources/Images/Ceila.png");
    StringProperty wallImg = new SimpleStringProperty("resources/Images/wall.png");
    StringProperty goalImg = new SimpleStringProperty("resources/Images/Goal.png");
    StringProperty pathImg = new SimpleStringProperty("resources/Images/path.png");

    public void setIcon(String s) throws FileNotFoundException {
        switch (s){
            case "Mike":
                playerIcon = new Image(new FileInputStream(MikeImg.get()));
                break;
            case "Sully":
                playerIcon = new Image(new FileInputStream(SullyImg.get()));
                break;
            case "Boo":
                playerIcon = new Image(new FileInputStream(BooImg.get()));
                break;
            case "Roz":
                playerIcon = new Image(new FileInputStream(RozImg.get()));
                break;
            case "Celia":
                playerIcon = new Image(new FileInputStream(CeliaImg.get()));
                break;
            default:
                break;
        }
    }

    public void drawMaze(int[][] maze, Stage stage) {
        this.maze = maze;
        draw(stage);
    }

    public int getPlayerR() {
        return playerR;
    }

    public static void setPlayerR(int playerR) {
        MazeDisplayer.playerR = playerR;
    }

    public int getPlayerC() {
        return playerC;
    }

    public static void setPlayerC(int playerC) {
        MazeDisplayer.playerC = playerC;
    }

    public static void setMyViewModel(MyViewModel myViewModel) {
        MazeDisplayer.myViewModel = myViewModel;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    void draw(Stage stage) {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            //define wall image
            Image wall=null;
            Image path = null;
            Image goal=null;

            try{
                wall = new Image(new FileInputStream(wallImg.get()));
                path = new Image(new FileInputStream(pathImg.get()));
                goal = new Image(new FileInputStream(goalImg.get()));
            }
            catch (Exception e)
            {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }

            //draw maze
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++)
                {
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(maze[i][j] == 1)
                        //if it is a wall:
                        graphicsContext.drawImage(wall,x,y,cellWidth,cellHeight);
                    else
                        graphicsContext.drawImage(path,x,y,cellWidth,cellHeight);
                    if(i==rows-1 && j==cols-1)
                    {
                        graphicsContext.drawImage(goal,x,y,cellWidth,cellHeight);
                    }
                }
            }

            //set goal image


            //add player icon to maze
            graphicsContext.drawImage(playerIcon,playerR*cellWidth,playerC*cellHeight,cellWidth,cellHeight);
            if (playerR == maze.length - 1 && playerC == maze[0].length - 1 && !winGame) {
                winGame=true;
                showWinnerStage("You Are The Winner", stage);
            }
        }
    }


    private void showWinnerStage(String youAreTheWinner, Stage stage) {
        try {
            PlayerIconController.BackGroundPlayer.stop();
            Stage stage1 = new Stage();
            stage1.setTitle(youAreTheWinner);

            FXMLLoader gameFXML = new FXMLLoader(MyViewController.class.getResource("Win.fxml"));
            Parent root = gameFXML.load();

            WinController winC = gameFXML.getController();
            winC.setMyViewModel(myViewModel);
            winC.setStage(stage1);
            Scene scene = new Scene(root, 640, 344);
            stage1.setScene(scene);
            winC.setScene(scene);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
