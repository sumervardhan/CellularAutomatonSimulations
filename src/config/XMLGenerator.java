package config;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class generates the XML file to be saved by the user; though the functionality is not yet implemented, it is also
 * meant to generate the XML file that will be implemented when the user puts in configuration parameters at the start of
 * the program. Currently, the class assumes that the file to be saved is the first scene of Game of Life; this is used
 * to test that the XMLGenerator actually works.
 * @author Ha Nguyen
 */

public class XMLGenerator {
    private Document myXMLDocument;
    private Element myRoot;
    private File myFileToSaveAs;
    private String mySimulationTypeValue;
    private String myXMLNumRows;
    private String myXMLNumCols;
    private String myXMLNumNeighbors;
    private String myXMLNeighborsConfiguration;
    private Map<String, String> myParametersMap;
    private Map<String, String> myStatesColorsMap;

    /**
     * Creates xml file using current simulation and saves it
     * Assumes file receives data for a simulation. If the user cancels the request to save a file, then the exception
     * is caught and the program continues on.
     *
     * @param fileToSaveAs the file name that the user
     */
    public XMLGenerator(File fileToSaveAs) {
        myFileToSaveAs = fileToSaveAs;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            myXMLDocument = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            //do nothing
        }
    }

    /**
     * Currently generates a hardcoded simulation, but in future would generate an xml file from current state of
     * simulation to be passed in
     * through the parser, and read in through the current state of the grid
     * Assumes 20 rows, 20 cols, 8 neighbors, and a specific neighbor configuration
     */
    public void generateSimulationXMLDocument() {
        myRoot = myXMLDocument.createElement("simulation");
        myXMLDocument.appendChild(myRoot);
        addAttribute(myRoot, "simulationType", "Game of Life");
        addColorsNode();
        Map<String, String> rowsColumnsNeighbors = new HashMap<>() {{
            put("num_rows", "20");
            put("num_cols", "20");
            put("num_neighbors", "8");
            put("neighbor_configuration", "-1 3 0 3 1 3");
        }};
        for (Map.Entry<String, String> entry : rowsColumnsNeighbors.entrySet()) {
            addChildNodeHelper(myRoot, entry.getKey(), entry.getValue());
        }
        addParametersNode();
        addGridNode();
        writeContentToXML();
    }

    private void addAttribute(Element parent, String attributeName, String attributeValue) {
        Attr attribute = myXMLDocument.createAttribute(attributeName);
        attribute.setValue(attributeValue);
        parent.setAttributeNode(attribute);
    }

    private void addParametersNode() {
        Element parametersNode = myXMLDocument.createElement("parameters");
        Map<String, String> parametersMap = new HashMap<>() {{
            put("min_population_threshold", "min_population_threshold:2");
            put("max_population_threshold", "max_population_threshold:3");
        }};
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            addChildNodeHelper(parametersNode, entry.getKey(), entry.getValue());
        }
        myRoot.appendChild(parametersNode);
    }

    private void addGridNode() {
        Element gridNode = myXMLDocument.createElement("initial_rectangular_grid");
        gridNode.setNodeValue("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        myRoot.appendChild(gridNode);
    }

    private void addColorsNode() {
        Element colorsNode = myXMLDocument.createElement("colors");
        Map<String, String> statesColorsMap = new HashMap<>() {{
            put("empty", "White");
            put("live", "Blue");
            put("dead", "Yellow");
        }};
        for (String key : statesColorsMap.keySet()) {
            addChildNodeHelper(colorsNode, key, statesColorsMap.get(key));
        }
        myRoot.appendChild(colorsNode);
    }

    private void addChildNodeHelper(Element parent, String childTagName, String textContent) {
        Element child = myXMLDocument.createElement(childTagName);
        child.setTextContent(textContent);
        parent.appendChild(child);
    }

    private void writeContentToXML() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(myXMLDocument);
            StreamResult result = new StreamResult(myFileToSaveAs);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            // this was implemented near the end of the project, so we have not had the chance to figure out the
            // error handling for this yet.
        } catch (NullPointerException e) {
            // this was implemented near the end of the project, so we have not had the chance to figure out the
            // error handling for this yet.
        }
    }
}
