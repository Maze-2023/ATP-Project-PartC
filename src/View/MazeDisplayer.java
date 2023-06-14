package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private static int playerR=0;
    private static int playerC=0;

    static MyViewModel myViewModel;

    public static MediaPlayer mediaPlayer;
    public boolean winGame=false;
    Image playerIcon;
    StringProperty MikeImg = new SimpleStringProperty("src/main/resources/Images/Mike.png");
    StringProperty SullyImg = new SimpleStringProperty("src/main/resources/Images/Sully.png");
    StringProperty BooImg = new SimpleStringProperty("src/main/resources/Images/boo.png");
    StringProperty RozImg = new SimpleStringProperty("src/main/resources/Images/roz.png");
    StringProperty CeliaImg = new SimpleStringProperty("src/main/resources/Images/Ceila.png");
    StringProperty wallImg = new SimpleStringProperty("src/main/resources/Images/wall.png");
    StringProperty goalImg = new SimpleStringProperty("src/main/resources/Images/Goal.png");
    StringProperty pathImg = new SimpleStringProperty("src/main/resources/Images/path.png");

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

    public void drawMaze(int[][] maze) {
        this.maze = maze;
        draw();
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

    void draw() {
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
            if (playerR == maze.length && playerC == maze[0].length && winGame==false) {
                winGame=true;
                showWinnerStage("You Are The Winner");
            }
        }
    }


    private void showWinnerStage(String youAreTheWinner) {
        //TODO: add winner stage to the program
    }
}
