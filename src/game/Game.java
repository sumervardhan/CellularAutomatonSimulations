package game;

import config.XMLException;
import config.XMLGameParser;
import config.XMLGenerator;
import config.XMLSimulationParser;
import simulation.GameOfLifeSimulation;
import simulation.PercolationSimulation;
import simulation.PredatorPreySimulation;
import simulation.SegregationSimulation;
import simulation.SpreadingOfFireSimulation;
import simulation.Simulation;

import elements.Grid;
import javafx.stage.FileChooser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * This class represents an instance of the Game, which is the entire Cellular Automata simulation. The class's purpose
 * is the main controller that is in charge of creating different instances of Simulations, Visualizations, Grid, etc.
 * and manage the passage of information between them.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Shreya Hurli
 */
public class Game {
    private static final String GAME_PROPERTIES = "GameProperties";
    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private String[] mySimulationButtons;
    private Stage myStage;
    private XMLSimulationParser mySimulationParser;
    private ResourceBundle myResources;

    public Game(Stage stage) {
        myResources = ResourceBundle.getBundle(GAME_PROPERTIES);

        myStage = stage;
        myTimeline = new Timeline();
        File gameConfig = new File("Resources/GameConfig.xml");
        if (! XMLException.isValidGameSchema(gameConfig)) {
            System.exit(0);
        }

        XMLGameParser parser = new XMLGameParser(gameConfig);
        mySimulationButtons = parser.getSimulationButtons();
        String windowTitle = parser.getTitle();
        int sceneWidthWithBar = parser.getSceneWidthFull();
        int sceneWidthJustCells = parser.getSceneWidth();
        int sceneHeight = parser.getSceneHeight();
        myVisualization = new Visualization(this, stage, mySimulationButtons, windowTitle, sceneWidthWithBar, sceneWidthJustCells, sceneHeight);
        myVisualization.showIntroScene();
    }

    /**
     * This method allows the Visualization to load the simulation file based on button clicking.
     * @param file the file to be loaded as picked on the UI
     */
    protected void loadSimulation(File file) {
        File simulationFile=null;
        if (XMLException.isValidSimulationSchema(file)) {
            simulationFile = file;
        } else {
            XMLException.showInvalidSimulationAlert(myResources);
        }

        Grid grid = new Grid(simulationFile);

        try {
            grid.configureCells();
        } catch (NoSuchElementException e) {
            XMLException.showGridInconsistencyAlert(myResources);
            return;
        }
        mySimulation = null;
        mySimulationParser = new XMLSimulationParser(simulationFile);
        if (mySimulationParser.getSimulationType().equals(myResources.getString("GameOfLife"))) {
            mySimulation = new GameOfLifeSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("Segregation"))) {
            mySimulation = new SegregationSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("PredatorAndPrey"))) {
            mySimulation = new PredatorPreySimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("SpreadingOfFire"))) {
            mySimulation = new SpreadingOfFireSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("Percolation"))) {
            mySimulation = new PercolationSimulation(grid);
        }
        myVisualization.showSimulationScene(grid);
        setGameLoop();
    }

    /**
     * This method allows the Visualization class to load the Intro scene when the Home button is clicked
     */
    protected void loadIntro() {
        myTimeline.getKeyFrames().clear();
        myTimeline.stop();
        myVisualization.showIntroScene();
    }

    /**
     * This method allows the Visualization class to adjust the speed of the simulation.
     * @param value the value to speed up or slow down the simulation by
     */
    protected void adjustSimulationSpeed(int value) {
        myTimeline.stop();
        myTimeline.getKeyFrames().clear();
        myFramesPerSecond += value;
        if(myFramesPerSecond < 1){
            myFramesPerSecond = 1;
        }
        myMillisecondDelay = 1000 / myFramesPerSecond;
        var frame = new KeyFrame(Duration.millis(myMillisecondDelay), e -> playGameLoop());
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
    }

    /**
     * This method allows the user to pause the simulation through the Visualization class.
     */
    protected void pauseSimulation(){
        myTimeline.stop();
    }

    /**
     * This method allows the Visualization class to resume the simulation.
     */
    protected void playSimulation(){
        myTimeline.play();
    }

    /**
     * When on pause, this method allows the user to skip a step in the visualization.
     */
    protected void skipStep(){
            playGameLoop();
    }

    /**
     * This allows the Visualization class to load an input file as selected by the user
     */
    protected void loadUserInputFile() {
        myTimeline.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Resources/simulation_config_files"));
        File selectedFile  = fileChooser.showOpenDialog(myStage);
        try {
            loadSimulation(selectedFile);
        } catch (IllegalArgumentException | NullPointerException e) {
           // do nothing
        }
    }

    /**
     * This allows the visualization class to save an input file when the User selects the save the file.
     */
    protected void saveSimulationXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(myStage);
        XMLGenerator xmlGenerator = new XMLGenerator(file);
        xmlGenerator.generateSimulationXMLDocument();
    }

    private void setGameLoop() {
        var frame = new KeyFrame(Duration.millis(myMillisecondDelay), e -> playGameLoop());
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
        pauseSimulation();
    }

    private void playGameLoop() {
        mySimulation.analyzeCells();
        mySimulation.updateCells();
        myVisualization.displayGrid(mySimulation.getGrid());
    }

}
