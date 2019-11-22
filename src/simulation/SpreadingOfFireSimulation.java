package simulation;

import config.XMLParser;
import elements.Cell;
import elements.Grid;

import java.util.Random;

/**
 * This class represents the Segregation Simulation. It is used by Game to run the Segregation Simulation if that is
 * the file that the user selected.
 * @author Shreya Hurli
 */
public class SpreadingOfFireSimulation extends Simulation {
    private final static int EMPTY = 0;
    private final static int TREE = 1;
    private final static int BURNING = 2;

    private XMLParser myXMLParser;
    private double myBurnProbability;
    private double myTreeProbability;

    private int myEmptyTurns;

    public SpreadingOfFireSimulation(Grid grid) {
        super(grid);
        myBurnProbability = 1;
        myTreeProbability = 0;
    }

    /**
     * Overrides analyzeCells in the Simulation superclass and analyzes the cells for the simulation based on the specified
     * rules of Spreading of Fire.
     */
    @Override
    public void analyzeCells(){
        for(Cell cell: getGrid()){
            if (cell.getMyID() == 37) {

                System.out.println("Cell: " + cell.getMyID() + " \n");
                for (Cell neighbor : cell.getMyNeighbors()) {
                    System.out.print(neighbor.getMyID() + ", ");
                }
                System.out.println();
            }
                if(cell.getState() == EMPTY){
                    myEmptyTurns = 1;
                    willTreeGrow(cell, cell.getMyNeighbors());
                }
                if(cell.getState() == TREE){
                    willBurn(cell, cell.getMyNeighbors());
                }
                if(cell.getState() == BURNING){
                    cell.setMyNextState(EMPTY);
                }
            }
        }

    private void willBurn(Cell curr, Cell[] neighbors){
        for(Cell neighbor : neighbors){
            if(curr.getState() == TREE && neighbor.getState() == BURNING && probability(myBurnProbability)){
                curr.setMyNextState(BURNING);
            }
        }
    }

    private void willTreeGrow(Cell curr, Cell[] neighbors){
        if(curr.getState() == EMPTY && myEmptyTurns == 1){
            curr.setMyNextState(EMPTY);
            myEmptyTurns++;
        }
        for(Cell neighbor : neighbors){
            if(curr.getState() == EMPTY && neighbor.getState() == TREE &&
                    probability(myTreeProbability) && myEmptyTurns == 2){
                curr.setMyNextState(TREE);
            }
        }
    }

    private boolean probability(double probEvent){
        Random random = new Random();
        int randNum = random.nextInt(100);

        return (randNum < (probEvent * 100) - 1);
    }
}

