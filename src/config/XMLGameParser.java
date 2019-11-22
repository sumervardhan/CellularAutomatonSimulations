package config;

import java.io.File;

/**
 * This class parses the Game configuration; basically everything that remains constant in the game that should be set
 * up at the very beginning. The class depends on the GameConfig.xml, which is validated against the GameConfig schema.
 *
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Robert Duvall (as taken from the class code).
 */
public class XMLGameParser extends XMLParser {

    /**
     * constructs an xml game parser that takes in a file and parses it
     * @param file the XML file that specifies the requirements for the game to be parsed
     */
    public XMLGameParser(File file) {
        super(file);
    }

    /**
     * Get title of the simulation as specified in the XML
     * @return the title for the window of the simulation
     */
    public String getTitle() {
        return super.getRoot().getAttribute("title");
    }

    /**
     * Get the scene width including the bar for the button
     * @return the scene width to be used as dimensions for the game
     */
    public int getSceneWidthFull() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_width_full").item(0).getTextContent());
    }

    /**
     * Get the scene width for just the square where the simulation will appear; useful for dividing by the cells
     * in order to get their proper sizes.
     * @return the adjusted scene width when the control bars are subtracted.
     */
    public int getSceneWidth() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_width_cells").item(0).getTextContent());
    }

    /**
     * Get the scene height for the simulation; useful for dividing by in order to get the cells and for setting up the stage.
     * @return the scene height of the window that we want to be specified.
     */
    public int getSceneHeight() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_height").item(0).getTextContent());
    }

    /**
     * Gets the buttons to be shown for each of the simulation scene; this assumes that each scene will have the same button controls.
     * As such, it is set up in the game.
     * @return all the simulation buttons text to be shown on the intro scene.
     */
    public String[] getSimulationButtons() {
        String[] buttons = super.getRoot().getElementsByTagName("simulation_buttons").item(0).getTextContent()
                .trim().split("\\s+");
        for (int i=0; i<buttons.length; i++) {
            buttons[i] = buttons[i].replaceAll("/", " ");
        }
        return buttons;
    }

    /**
     * Gets the button for the intro scene, which is currently for loading a file.
     * @return the text for the intro button.
     */
    public String getIntroButton() {
        return super.getRoot().getElementsByTagName("intro_buttons").item(0).getTextContent().trim();
    }

}

