package simulation;

import elements.Grid;

/**
 * This abstract class represents a Simulation. It is a superclass that is subclassed by each of the Simulation in order
 * to implement their own rules on the Simulation.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Shreya Hurli
 */
public abstract class Simulation {
    private Grid myGrid;

    /**
     * Initializes the simulation with the Grid object given by game.
     * @param grid the grid object to initialize the simulation with.
     */
    public Simulation(Grid grid) {
        myGrid = grid;
    }

    /**
     * Each simulation class implements their own version of this method to analyze cells and determine the next state
     * of each cell depending on its neighbors.
     */
    public abstract void analyzeCells();

    /**
     * This method in Game is called to update the state of each cell once all the neighbors analysis has finished.
     */
    public void updateCells() {
        for(int id = 0; id < myGrid.getSize(); id++){
            myGrid.getCell(id).updateState();
        }
    }

    /**
     * This method is used to get the Grid of the Simulation; this is useful for the Simulation class, which must display
     * this grid to the GUI
     * @return the current grid of the Simulation.
     */
    public Grid getGrid() {
        return myGrid;
    }
}