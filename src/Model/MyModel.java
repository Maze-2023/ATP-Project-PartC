package Model;

import IO.MyDecompressorInputStream;
import Server.*;
import Client.*;
import View.HelloApplication;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.lang.module.Configuration;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.concurrent.locks.Condition;

public class MyModel extends Observable implements IModel{
    Maze maze;
    Solution solution;
    int playerR;
    int playerC;
    int EmptyRow;
    int EmptyCol;
    private Server generatorServer;
    private Server solverServer;
    private boolean serversOn = false;

    /**
     * Constructor - creates new servers (one for generate and one for solve)
     * Calls "startServers()" to start the servers.
     */
    public MyModel() {
        generatorServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solverServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        startServers();
    }
    // Run the servers
    public void startServers() {
        serversOn = true;
        generatorServer.start();
        solverServer.start();
    }
    // Stop the servers
    public void stopServers() {
        if(serversOn){
            generatorServer.stop();
            solverServer.stop();
        }
    }

    @Override
    public void generate(int r, int c) {
        CommunicateWithServer_MazeGenerating(r,c);
        setChanged();
        notifyObservers("generated");
    }

    @Override
    public void setEmptyMaze(int r, int c) {
        EmptyRow = r;
        EmptyCol = c;
    }

    // getters setters
    @Override
    public int getPlayerR(){return playerR;}
    @Override
    public int getPlayerC(){return playerC;}
    @Override
    public void setPlayerR(int row){playerR = row;}
    @Override
    public void setPlayerC(int col){playerC = col;}

    public int[][] getFrame(){return maze.getFrame();}

    @Override
    public void addToMe(Observer o){
        this.addObserver(o);
    }
    private void CommunicateWithServer_MazeGenerating(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy()
            {
                @Override
                public void clientStrategy(InputStream inFromServer,
                                           OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[rows * cols + 10]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        playerR = maze.getStartPosition().getRowIndex();
                        playerC = maze.getStartPosition().getColumnIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                        HelloApplication.logger.error("No maze");
                    }
                }
            });
            client.communicateWithServer();
            HelloApplication.logger.info(client + " " + rows + "," + cols);
        } catch (Exception e) {
            e.printStackTrace();
            HelloApplication.logger.error("No client");
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                solution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                            } catch (Exception e) {
                                e.printStackTrace();
                                HelloApplication.logger.error("No solution");
                            }
                        }
                    });
            client.communicateWithServer();
            HelloApplication.logger.info(client + " " + Configurations.getInstance().properties.getProperty("mazeSearchingAlgorithm"));
        } catch (Exception e) {
            e.printStackTrace();
            HelloApplication.logger.error("No client");
        }
    }

    @Override
    /**
     * Move the player according to keyboard press
     */
    public void movePlayer(KeyCode dir) {
        int newRow = playerR;
        int newCol = playerC;

        switch (dir) {
            // UP
            case DIGIT8 -> newCol = playerC - 1;

            // DOWN
            case DIGIT2 -> newCol = playerC + 1;

            // RIGHT - works
            case DIGIT6 -> newRow = playerR + 1;

            // LEFT - works
            case DIGIT4 -> newRow = playerR - 1;

            /*----------------------diagonal moves---------------------*/
            case DIGIT1 -> {
                newRow = playerR - 1;
                newCol = playerC + 1;
            }
            case DIGIT3 -> {
                newRow = playerR + 1;
                newCol = playerC + 1;
            }
            case DIGIT9 -> {
                newRow = playerR + 1;
                newCol = playerC - 1;
            }
            case DIGIT7 -> {
                newRow = playerR - 1;
                newCol = playerC - 1;
            }
        }

        // Check if the new position is valid and not blocked by a wall
        if (isValidMove(newRow, newCol)) {
            playerR = newRow;
            playerC = newCol;
            setChanged();
            notifyObservers("playerMove");
        }
    }

    private boolean isValidMove(int row, int col) {
        // Check if the new position is within the maze bounds

        if (row < 0 || row >= maze.getRows() || col < 0 || col >= maze.getColumn()) {
            return false;
        }

        // Check if the new position is blocked by a wall
        return maze.getCellValue(col, row) != 1;
    }

    public void solve(){
        CommunicateWithServer_SolveSearchProblem();
        setChanged();
        notifyObservers("solved");
    }

    @Override
    public ArrayList<int[]> solveMaze() {
        CommunicateWithServer_SolveSearchProblem();
        ArrayList<AState> sol = solution.getSolutionPath();
        ArrayList<int[]> intSol = new ArrayList<>();
        for (AState aState : sol) {
            if (aState instanceof MazeState M) {
                Position P = M.getPosition();
                int[] tmp = new int[2];
                tmp[0] = P.getRowIndex();
                tmp[1] = P.getColumnIndex();
                intSol.add(tmp);
            }
        }
        return intSol;
    }

    @Override
    public void save(File file) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(maze.toByteArray());
            os.flush();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void loadGame(File loadfile) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(loadfile));
        byte[] loadedMaze = (byte[]) objectIn.readObject();
        objectIn.close();
        maze = new Maze(loadedMaze);
        setChanged();
        notifyObservers("loaded");
    }

    @Override
    public void exit() {
        stopServers();
    }
}
