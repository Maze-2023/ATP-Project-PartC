package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private int playerR=0;
    private int playerC=0;

    private boolean winGame=false;
    Image playerIcon;
    StringProperty MikeImg = new SimpleStringProperty();
    StringProperty SullyImg = new SimpleStringProperty();
    StringProperty BooImg = new SimpleStringProperty();
    StringProperty RozImg = new SimpleStringProperty();
    StringProperty CeliaImg = new SimpleStringProperty();
    StringProperty wallImg = new SimpleStringProperty();
    StringProperty goalImg = new SimpleStringProperty();

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

    public void setPlayerR(int playerR) {
        this.playerR = playerR;
    }

    public int getPlayerC() {
        return playerC;
    }

    public void setPlayerC(int playerC) {
        this.playerC = playerC;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    private void draw() {
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
            try{
                wall = new Image(new FileInputStream(wallImg.get()));
            }
            catch (Exception e)
            {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }

            //draw maze
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(maze[i][j] == 1){
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        if(wall == null)
                            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                        else
                            graphicsContext.drawImage(wall,x,y,cellWidth,cellHeight);
                    }
                }
            }

            //set goal image
            Image goal=null;
            try{
                goal = new Image(new FileInputStream(goalImg.get()));
            }
            catch (Exception e)
            {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
            graphicsContext.drawImage(goal,maze.length*cellWidth,maze[0].length*cellHeight,cellWidth,cellHeight);

            //add player icon to maze
            graphicsContext.drawImage(playerIcon,playerR*cellWidth,playerC*canvasHeight,cellWidth,cellHeight);
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
