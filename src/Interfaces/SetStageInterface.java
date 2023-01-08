package Interfaces;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Functional Interface class for using lambda to set the scene
 * @author Steve Corwin Amalfitano
 */
public interface SetStageInterface {

    /**
     * Method for setting the scene
     */
    void setStage(ActionEvent actionEvent, String path, float width, float height) throws IOException;
}
