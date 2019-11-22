package simulation;

import config.XMLParser;
import elements.Cell;
import elements.Grid;

/**
 * This class represents the Game of Life Simulation. It is used by Game to run the Game of Life Simulation if that is
 * the file that the user selected.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Shreya Hurli
 */
public class GameOfLifeSimulation extends Simulation {
    public static final int LIVE = 1;
    public static final int DEAD = 2;

    private int minPopulationThreshold;
    private int maxPopulationThreshold;


    public GameOfLifeSimulation(Grid grid) {
        super(grid);
        minPopulationThreshold = 2;
        maxPopulationThreshold = 3;
    }

    /**
     * Overrides analyzeCells in the Simulation superclass and analyzes the cells for the simulation based on the specified
     * rules of the Game of Life.
     */
    @Override
    public void analyzeCells() {
        for(Cell cell: getGrid()){
                int liveNeighborsCount = countLiveNeighbors(cell.getMyNeighbors());
                if (cell.getState() == LIVE) {
                    if (liveNeighborsCount < minPopulationThreshold || liveNeighborsCount > maxPopulationThreshold) {
                        cell.setMyNextState(DEAD);
                    }
                } else if (cell.getState() == DEAD) {
                    if (liveNeighborsCount == maxPopulationThreshold) {
                        cell.setMyNextState(LIVE);
                    }
                }
            }
        }


    private int countLiveNeighbors(Cell[] neighbors) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == LIVE) {
                liveNeighborsCount++;
            }
        }
        return liveNeighborsCount;
    }
}
