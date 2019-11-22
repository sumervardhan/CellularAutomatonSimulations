package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * This class is the parent class for XMLParser; it holds instance variables such as
 * the file being passed in, as well as methods that are shared by both the SimulationXML
 * Parser and the GameParser.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Shreya Hurli
 * @author Robert Duvall (taken from class lecture code)
 */
public class XMLParser {
    private static final String GAME_PROPERTIES = "GameProperties";
    private ResourceBundle myResources;

    private final String ROOT_NAME;
    private final DocumentBuilder DOCUMENT_BUILDER;
    private File myFile;
    private Element myRoot;

    public XMLParser(File file) {
        myResources = ResourceBundle.getBundle(GAME_PROPERTIES);

        DOCUMENT_BUILDER = getDocumentBuilder();
        myFile = file;
        try {
            myRoot = getRootElement(myFile);
        } catch (XMLException e) {
            e.showInvalidFileAlert(myResources);
        }
        ROOT_NAME = myRoot.getTagName();
    }

    protected Element getRoot() {
        return myRoot;
    }

    private Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    private DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
