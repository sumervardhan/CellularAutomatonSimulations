package game;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is used to run the program and set the stage.
 * @author Ha Nguyen
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Game ca = new Game(stage);
    }
}
