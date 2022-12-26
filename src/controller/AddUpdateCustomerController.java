package controller;

import classes.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddUpdateCustomerController {
    private static Customer modifiedCust = null;
    public static void setModifiedCust(Customer customer) {
        modifiedCust = customer;
    }
    public void onCustCancel(ActionEvent actionEvent) throws IOException {
        modifiedCust = null;
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
