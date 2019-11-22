package elements;

import javafx.scene.shape.Shape;

import java.util.List;

/**
 * This class represents the cells in each of the simulation. The class is used by Grid to initialize the grid object itself,
 * used by Simulations to change and update the state of each cell according to its neighbors, and used by Visualization
 * to print out the colors that represent the state of the cells.
 * @author Sumer Vardhan
 */
public class Cell implements Comparable<Cell>{
    private int myState;
    private int myNextState;
    private List<Cell> myNeighbors;
    private int myID;
    private boolean myIsAvailable;
    private Shape myShape;

    public Cell(int state, int ID){
        myState = state;
        myNextState = state;
        myID = ID;
        myIsAvailable = true;
    }

    /**
     * Gets the integer that represents the state of the cell, used by classes such as Grid, Simulation, and Visualization
     * to configure the grid object to display to the user and update this grid.
     * @return the integer that represents the state of each cell.
     */
    public int getState(){
        return myState;
    }

    /**
     * Updates the cell's state to the next state set by the Simulation class. This is done so that all cells update
     * their states at once.
     */
    public void updateState(){
        myState = myNextState;
        myIsAvailable = true;
    }

    /**
     * Gets the next state of the cell; this is used primarily by Predator and Prey because all fish cells move first
     * and update their next state before shark cells move accordingly
     * @return the next5 state of the cell
     */
    public int getNextState() {
        return myNextState;
    }

    /**
     * Sets the next state of the cell for it to update to this state once all cells have checked their neighbors.
     * @param state the integer that represents the next state that the cell should be.
     */
    public void setMyNextState(int state){
        myNextState = state;
    }

    /**
     * Gets the neighbors of the current cell, used so that simulation classes can check the state of each cell's neighbors.
     * @return the array of the neighbors of the current cell.
     */
    public Cell[] getMyNeighbors(){
        return myNeighbors.toArray(new Cell[0]);
    }

    /**
     * Sets the neighbor of the current cell, used for Grid to initialize cell neighbors
     * @param neighbors the list of neighbors this cell should add to as its neighbor
     */
    public void setMyNeighbors(List<Cell> neighbors) {
        myNeighbors = neighbors;
    }

    /**
     * Used to indicate whether a cell has been taken up by another cell during the simulation running process; this way,
     * no two cells can make a move on an empty cell or a cell that they both want to occupy.
     * @return the boolean value that represents whether a cell is available.
     */
    public boolean getMyIsAvailable() {
        return myIsAvailable;
    }

    /**
     * Used to set the available variable to indicate that the cell has been taken up/its next state has already
     * been set by the simulation, so another cell should not come in and set the next state in order to avoid overwriting it
     * @param value the boolean value to be set to if a cell's next state is available or not available.
     */
    public void setMyIsAvailable(boolean value) {
        myIsAvailable = value;
    }

    /**
     * Used to get the Id of the cell in order to index into it.
     * @return the cell's id
     */
    public int getMyID(){
        return myID;
    }

    /**
     * Used to get the shape of the cell if needed for simulations
     * @return the cell's shape
     */
    public Shape getMyShape(){
        return myShape;
    }

    /**
     * Overwrites compareTo in order to compare cells to iterate over them
     * @param o the cell to be compared to this cell
     * @return 0 if the cells' id are equal, -1 if this cell is comes before the cell in the parameter, and 1 if this
     * cell comes after it.
     */
    @Override
    public int compareTo(Cell o) {
        if(this.getMyID() == o.getMyID()) {
            return 0;
        }
        else if (this.getMyID() < o.getMyID()){
            return -1;
        }
        else{
            return 1;
        }
    }
}
