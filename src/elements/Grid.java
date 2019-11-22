package elements;

import config.XMLSimulationParser;

import java.io.File;
import java.util.*;

/**
 * The grid class that holds all the cells for the Simulation. This class is configured by Game and used by Simulation
 * to run analysis on each cell, as well as by Visualzation in order to display the state to the user.
 * @author Sumer Vardhan
 */
public class Grid implements Iterable<Cell> {
    private File myConfigFile;
    private XMLSimulationParser myXMLParser;
    private Scanner mySc;
    private String[] myCellColors;
    private Set<Cell> myCells;
    private int myNumRows;
    private int myNumCols;
    private String myNeighborConfiguration;

    public Grid(File file){
        myConfigFile = file;
        myXMLParser =  new XMLSimulationParser(myConfigFile);
        mySc = new Scanner(myXMLParser.getInitialGrid());
        myCellColors = myXMLParser.getColors();
        myNumRows = myXMLParser.getNumRows();
        myNumCols = myXMLParser.getNumCols();
        myCells = new TreeSet<>();
    }

    /**
     * Allows the Game class to configure the cells upon initialization
     * @return the set of cells that currently populate the grid
     * @throws NoSuchElementException when the grid size is inconsistent, so there are rows specified in the XML that
     * cannot be populated.
     */
    public Set<Cell> configureCells() throws NoSuchElementException{
        createGridOfCells();
        setCellNeighbors();
        return myCells;
    }

    /**
     * Gets individual cells in the grid according to Ids. This is used by the Simulation classes to analyze cells and
     * by the Visualization
     * @param i the id of the cell that we want to get
     * @return the cell at the id specified
     */
    public Cell getCell(int i){
        for(Cell cell: myCells){
            if(cell.getMyID() == i){
                return cell;
            }
        }
        return null;
    }

    /**
     * Gets all the empty cells in the grid, which is useful for the Segregation simulation because this allows the
     * Segregation simulation to randomly choose the empty cells to move to.
     * @return the list of empty cells currently in the grid.
     */
    public List<Cell> getEmptyCells(){
        List<Cell> emptyCells = new ArrayList<>();
        int i = 0;
        for(Cell cell : myCells){
            if(cell.getState() == 0)
            {
                emptyCells.add(cell);
            }
        }

        return emptyCells;
    }

    /**
     * Gets the number of rows in the grid, which is useful for Visualization to display the grid
     * @return the number of rows
     */
    public int getNumRows(){
        return myNumRows;
    }

    /**
     * Gets the number of columns in the grid, which is useful for Visualization to display the grid
     * @return the number of columns
     */
    public int getNumCols(){
        return myNumCols;
    }

    /**
     * Gets the array that holds the cell colors for each of the state, which is useful for simulation to
     * display the state of the cells.
     * @return the arary that holds the cell colors for each of the state
     */
    public String[] getCellColors(){
        return myCellColors;
    }

    private void createGridOfCells() throws NoSuchElementException {
        int id = 0;
        while(mySc.hasNext()){
            for (int i = 0; i < myNumRows; i++){
                for (int j = 0; j < myNumCols; j++){
                        int state = mySc.nextInt();
                        myCells.add(new Cell(state, id));
                        id++;
                }
            }
        }
    }

    private void setCellNeighbors() {
        for(Cell cell: this){
            List<Cell> neighbors = null;
            int cell_row = cell.getMyID()/(myNumCols);
            int cell_column = cell.getMyID()%(myNumRows);
            neighbors = checkNeighborsForCell(cell_row, cell_column);
            cell.setMyNeighbors(neighbors);
            if (cell.getMyID() == 37) {
                for(Cell neighbor: cell.getMyNeighbors()) {
                }
            }
        }
    }

    /**
     * Gets the configuration file of the Grid, which is useful for the simulation classes to be able to create a
     * parser object off the file
     * @return the configuration file for the grid
     */
    public File getMyConfigFile(){
        return myConfigFile;
    }

    /**
     * Gets the size of the grid, which is useful for the Simulation and Visualization classes to figure out the grid's
     * dimensions to set cells and display them accordingly
     * @return the grid's size represented by its number of rows times number of columns
     */
    public int getSize() {
        return myNumRows*myNumCols;
    }

    /**
     * Creates an iterator that allows for iterating over all the cells
     * @return the Iterator that allows for iterating over each of the cell in the grid
     */
    @Override
    public Iterator<Cell> iterator() {
        return this.myCells.iterator();
    }

    private int toCellID(int row, int column){
        return row*myNumCols + column;
    }

    //This class takes pattern as input from xml
    private List<Cell> checkNeighborsForCell(int row, int column){
        //The "pattern" around the cell in question used to determine its neighborhood comes from config
        int original_column = column;
        int original_row = row;
        ArrayList<Cell> neighbors = new ArrayList<>();
        myNeighborConfiguration = myXMLParser.getNeighborConfiguration();
        Scanner sc = new Scanner(myNeighborConfiguration);
        while(sc.hasNextInt()){
            int row_modifier = sc.nextInt();
            int col_modifier = sc.nextInt();
            row = original_row + row_modifier;
            int number_of_neighbors_in_row = sc.nextInt();
            for(int i = 0; i < number_of_neighbors_in_row; i++){
                column = original_column + col_modifier + i;
                if(column > -1 && column < myNumCols && row > -1 && row < myNumRows) {
                    if(column != original_column || row != original_row) {
                        neighbors.add(getCell(toCellID(row, column)));
                    }
                }
            }
        }
        return neighbors;
    }
}