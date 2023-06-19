package Model;

import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {

    void generate(int r, int c);
    void setEmptyMaze(int r, int c);
    int getPlayerR();
    int getPlayerC();
    void setPlayerR(int row);
    void setPlayerC(int col);
    ArrayList<int[]> solveMaze();
    void movePlayer(KeyCode dir);
    int[][] getFrame();
    void addToMe(Observer o);
    void save(File file);

    void loadGame(File loadfile) throws IOException, ClassNotFoundException;
    void exit();

    void solve();
}
