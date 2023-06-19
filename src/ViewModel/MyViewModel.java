package ViewModel;

import Model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.input.KeyCode;

/**
 * This class connect between the View to Model
 * It's also used as Observer
 */

public class MyViewModel extends Observable implements Observer {

    IModel M;
    int playerR ;
    int playerC;
    public StringProperty stringPlayerR = new SimpleStringProperty();
    public StringProperty stringPlayerC = new SimpleStringProperty();
    public int[][] emptyMaze;
    public static boolean IsEmpty;
    int EmptyRow;
    int EmptyCol;

    // constructor
    public MyViewModel(IModel m){
        M = m;
        IsEmpty = false;
        M.addToMe(this);
    }

    public int getPlayerR() {return playerR;}
    public int getPlayerC() {return playerC;}
    public void addToMe(Observer o){
        this.addObserver(o);
    }

    public int[][] getFrame(){
        return M.getFrame();
    }
    public void createNewGame(int rows, int columns) {
        M.generate(rows,columns);
    }

    /**
     * Create an empty maze by algorithm choice
     * @param row of maze
     * @param col of the maze
     */
    public void createEmptyMaze(int row, int col) {
        IsEmpty = true;
        EmptyRow=row;
        EmptyCol=col;
        emptyMaze=new int[row][col];
        for (int e=0;e<EmptyRow;e++){
            for (int k=0;k<EmptyCol;k++){
                emptyMaze[e][k]=0;
            }
        }
        setChanged();
        notifyObservers("empty");
    }

    // update player position
    private void updatePose(){
        playerR = M.getPlayerR();
        playerC = M.getPlayerC();
        stringPlayerR.set(playerR + "");
        stringPlayerC.set(playerC + "");
    }

    /**
     * Choose an option by the observer notify
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o == M)
        {
            String choice = arg.toString();
            switch (choice) {
                case "generated" -> {
                    setChanged();
                    updatePose();
                    notifyObservers("generated");
                }
                case "playerMove" -> {
                    updatePose();
                    setChanged();
                    notifyObservers("playerMove");
                }
                case "solved" -> {
                    setChanged();
                    notifyObservers("solved");
                }
                case "loaded" -> {
                    setChanged();
                    notifyObservers("loaded");
                }
                default -> {
                }
            }
        }
    }

    /**
     * Move player
     * @param direction to move
     */
    public void movePlayer(KeyCode direction){
        if(IsEmpty)
            M.setEmptyMaze(emptyMaze.length, emptyMaze[0].length);
        M.movePlayer(direction);
    }

    public void saveM(File file ){
        M.save(file);
    }

    public void loadGame(File loadFile) throws IOException, ClassNotFoundException {
        M.loadGame(loadFile);
    }

    public void exit() {
        M.exit();
    }

    public ArrayList<int[]> getSolution() {
        return M.solveMaze();
    }

    public void solve()
    {
        M.solve();
    }
}
