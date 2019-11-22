package config;

import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

/**
 * This class represents the parser for Simulation XML files, which the users would load in and each of the simulation
 * would start. The class parses all XML Simulation files and their file specific elements and attributes.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 */
public class XMLSimulationParser extends XMLParser {

    public static final String SIMULATION_TYPE_ATTRIBUTE = "simulationType";

    /**
     * Creates and XMLSimulationParser file object; this calls the XMLParser constructor and sets up the instance variables
     * there.
     * @param file the file that the user has passed in to use as the configuration.
     */
    public XMLSimulationParser(File file) {
        super(file);
    }

    /**
     * Returns the simulation type of the file, including Game of Life, Percolation, PredatorPrey, Segregation, and
     * Spreading of Fire.
     * @return the simulation type specified at the simulationType attribute.
     */
    public String getSimulationType() {
        return super.getRoot().getAttribute(SIMULATION_TYPE_ATTRIBUTE);
    }

    /**
     * Used to get the number of rows specified in the Configuration file. "num_rows" is verified to have existed at
     * the beginning when the parser is first created.
     * @return the number of rows specified for the simulation
     */
    public int getNumRows() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_rows").item(0).getTextContent());
    }

    /**
     * Used to get the number of rows specified in the Configuration file. "num_rows" is verified to have existed at
     * the beginning when the parser is first created.
     *  @return the number of rows specified for the simulation
     */
    public int getNumCols() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_columns").item(0).getTextContent());
    }

    /**
     * Used to get the initial grid configuration specified in the Simulation config file. This is used by the grid to
     * actually create and display the simulation.
     * @return the initial grid configuration specified for the simulation.
     */
    public String getInitialGrid() {
        return super.getRoot().getElementsByTagName("initial_rectangular_grid").item(0).getTextContent();
    }

    /**
     * Used to get the colors for each of the state as specified in the configuration file.
     * @return the array of color names specified for each of the state.
     */
    public String[] getColors() {
         NodeList colorNodes = super.getRoot().getElementsByTagName("colors");
         String[] colors = colorNodes.item(0).getTextContent().trim().split("\\s+");
         return colors;
    }

    private Map<Integer, List<Integer>> getNeighborRulesHelper(String neighborRules) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        String s = super.getRoot().getElementsByTagName(neighborRules).item(0).getTextContent();
        if (s.length() == 0) {
            return map;
        }
        for (String sub : s.split("/")) {
            String[] subSplit = sub.split("c");
            Integer key = Integer.parseInt(subSplit[0].replaceAll(" ", ""));
            List<Integer> values = new ArrayList<>();
            String[] valuesAsStrings = subSplit[1].trim().split("\\s");
            for (String value : valuesAsStrings) {
                values.add(Integer.parseInt(value));
            }
            map.put(key, values);
        }
        return map;
    }

    /**
     * Used to get the parameters required for each simulation, such as the segregation threshold, the threshold for
     * overpopulation, the probability of the fire burning, etc.
     * @return the parameters specified from the configuration file.
     */
    public Map<String, Double> getParameters() {
        Map<String, Double> parameters = new HashMap<>();
        NodeList parameterNodes = super.getRoot().getElementsByTagName("parameters");
        String parametersAsOneString = parameterNodes.item(0).getTextContent().trim();
        String[] parametersStringArray = parametersAsOneString.split("\\s+");
        for (String parameter : parametersStringArray) {
            String[] keyValueArray = parameter.split(":");
            parameters.put(keyValueArray[0], Double.parseDouble(keyValueArray[1]));
        }
        return parameters;
    }

    /**
     * Used to get the neighbor positions specified in the configuration file, used for setting up the neighbors
     * @return the neighbor positions as specified in the configuration file
     */
    public String getNeighborConfiguration(){
        return super.getRoot().getElementsByTagName("neighbor_configuration").item(0).getTextContent();
    }

}
