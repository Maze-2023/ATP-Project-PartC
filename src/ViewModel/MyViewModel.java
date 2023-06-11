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

    public MyViewModel(IModel m){
        M = m;
        IsEmpty = false;
        M.addToMe(this);
    }

    public int getPlayerR() {return playerR;}
    public int getPlayerC() {return playerC;}
    public ArrayList<int[]> getSol(){
        return M.solveMaze();
    }
    public void addToMe(Observer o){
        this.addObserver(o);
    }

    public int[][] getFrame(){
        return M.getFrame();
    }
    public void createNewGame(int rows, int columns) {
        M.generate(rows,columns);
    }

    private void updatePose(){
        playerR = M.getPlayerR();
        playerC = M.getPlayerC();
        stringPlayerR.set(playerR + "");
        stringPlayerC.set(playerC + "");
    }
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
}
