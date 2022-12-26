package controller;

import classes.Appt;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddUpdateApptsController {
    private static Appt modifiedAppt = null;
    public static void setModifiedAppt(Appt appt) {
        modifiedAppt = appt;
    }

    public void onApptCancel(ActionEvent actionEvent) throws IOException {
        modifiedAppt = null;
        backToMain(actionEvent);
    }

    private void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
