package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/** FUTURE ENHANCEMENTS - This class creates the AMALFI Makes It Inventory Management Application.
 * Adding a server side database to the application
 * for permanent storage of parts and products would greatly enhance the functionality
 * of the application and would be a necessary step for converting the app to a production
 * level software version
 * @author Steve Corwin Amalfitano
 */

public class Main extends Application {

    /** The main method that launches the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** The start method sets the stage and starts the first scene (mainScreen.fxml).
     * javadoc files located at src/main/javadoc
     * @param openingStage This is the stage passed to the start method.
     * @throws IOException Catches any exceptions thrown during data input / output.
     */
    @Override
    public void start(Stage openingStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/StartUpScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
//        scene.getStylesheets().add("/style.css");
        openingStage.setTitle("AMALFI SETS IT - Custom Scheduling");
        openingStage.getIcons().add(new Image("/icon3.png"));
        openingStage.setScene(scene);
        openingStage.show();

    }
}