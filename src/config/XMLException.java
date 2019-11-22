package config;

import javafx.scene.control.Alert;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class represents what might go wrong when using XML files. It handles showing alerts to notify the user of when
 * something has gone wrong.
 *
 * @author Robert C. Duvall
 * @author Ha Nguyen
 * @author Shreya Hurli
 */
public class XMLException extends RuntimeException {
    private static final String GAME_PROPERTIES = "GameProperties";
    private ResourceBundle myResources;

    // for serialization
    private static final long serialVersionUID = 1L;
    private static final List<String> XML_SIMULATION_VALIDATION_SCHEMAS = new ArrayList<>() {{
       add("Resources/simulation_config_schema/GameOfLifeConfig.xsd");
       add("Resources/simulation_config_schema/PercolationConfig.xsd");
       add("Resources/simulation_config_schema/PredatorPreyConfig.xsd");
       add("Resources/simulation_config_schema/SpreadingOfFireConfig.xsd");
       add("Resources/simulation_config_schema/SegregationConfig.xsd");
    }};
    private static final String XML_GAME_VALIDATION_SCHEMA = "Resources/GameConfig.xsd";

    /**
     * Create an exception based on an issue in our code.
     */
    public XMLException (String message, Object ... values) {
        super(String.format(message, values));
        myResources = ResourceBundle.getBundle(GAME_PROPERTIES);

    }

    /**
     * Checks to ensure the file provides a supported Simulation, creates an exception if it is not
     * @param file the file that the user has loaded in
     * @return boolean that indicates whether or not the file is a valid simulation file.
     */
    public static boolean isValidSimulationSchema(File file) {
        for (String xmlSchema : XML_SIMULATION_VALIDATION_SCHEMAS) {
            try {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File(xmlSchema));
                Validator validator = schema.newValidator();
                validator.validate(new StreamSource(file));
                return true;
            } catch (SAXException | IOException e) {
                // do nothing
            }
        }
        return false;
    }

    /**
     * Checks if the file passed in can be parsed by Game, if not, throws exception
     * @param file the file that the user has chosen to load in
     * @return boolean returns a boolena that indicates whether or not the file follows the valid schema
     */
    public static boolean isValidGameSchema(File file) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(XML_GAME_VALIDATION_SCHEMA));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }

    /**
     *  Produces the alert if invalid file is passed in
     * @param myResources the resource bundle that holds the key for displaying the error message.
     */
    public static void showInvalidFileAlert(ResourceBundle myResources) {
        Alert invalidFileAlert = new Alert(Alert.AlertType.ERROR);
        invalidFileAlert.setHeaderText(myResources.getString("invalidFileAlertHeader"));
        invalidFileAlert.setContentText(myResources.getString("invalidFileAlertMessage"));
        invalidFileAlert.showAndWait();
    }

    /**
     * Produces the alert if an invalid simulation is passed in
     * @param myResources the resource bundle that holds the key for displaying the error message.
     */
    public static void showInvalidSimulationAlert(ResourceBundle myResources) {
        Alert invalidSimulationAlert = new Alert(Alert.AlertType.ERROR);
        invalidSimulationAlert.setHeaderText(myResources.getString("invalidSimulationAlertHeader"));
        invalidSimulationAlert.setContentText(myResources.getString("invalidSimulationAlertMessage"));
        invalidSimulationAlert.show();
    }

    /**
     * Produces the alert if grid size passed in does not match the size of the actual grid in the XML file passed in
     * @param myResources the resource bundle that holds the key for displaying the error message.
     */
    public static void showGridInconsistencyAlert(ResourceBundle myResources) {
        Alert gridSizeInvalidAlert = new Alert(Alert.AlertType.ERROR);
        gridSizeInvalidAlert.setHeaderText(myResources.getString("invalidGridAlertHeader"));
        gridSizeInvalidAlert.setContentText(myResources.getString("invalidGridAlertMessage"));
        gridSizeInvalidAlert.showAndWait();
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public XMLException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public XMLException (Throwable cause) {
        super(cause);
    }
}
