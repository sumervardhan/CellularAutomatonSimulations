package simulation;

import elements.Cell;
import elements.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Percolation Simulation. It is used by Game to run the Percolation Simulation if that is
 * the file that the user selected.
 * @author Shreya Hurli
 */
public class PercolationSimulation extends Simulation {
    public static final int OPEN = 0;
    public static final int FULL = 1;
    public static final int BLOCKED = 2;

    public PercolationSimulation(Grid grid) { super(grid); }

    /**
     * Overrides analyzeCells in the Simulation superclass and analyzes the cells for the simulation based on the specified
     * rules of Percolation.
     */
    @Override
    public void analyzeCells(){
        for(Cell cell: getGrid()){
            Cell[] neighborsToFill = openNeighbors(cell.getMyNeighbors());
            if(cell.getState() == FULL && neighborsToFill.length != 0){
                for(Cell openNeighbor : neighborsToFill){
                    openNeighbor.setMyNextState(FULL);
                }
            }

        }
    }

    private Cell[] openNeighbors(Cell[] neighbors){
        List<Cell> openCells = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if(neighbor.getState() == OPEN){
                openCells.add(neighbor);
            }
        }
        return openCells.toArray(new Cell[0]);
    }
}
