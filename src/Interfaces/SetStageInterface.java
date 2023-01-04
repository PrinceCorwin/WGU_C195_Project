package Interfaces;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public interface SetStageInterface {
    void setStage(ActionEvent actionEvent, String path, float width, float height) throws IOException;
}
