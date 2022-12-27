package controller;

import classes.Appt;
import classes.Contact;
import classes.Customer;
import databaseHelp.sqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddUpdateApptsController {
    public TextField apptIdField;
    public TextField apptTitleField;
    public TextField apptStartField;
    public TextField apptEndField;
    public TextField apptTypeField;
    public TextField apptLocField;
    public TextField apptDescField;
    public TextField apptCustIdField;
    public TextField apptUserIdField;
    public ComboBox<String> apptContactField;

    public void initialize() {
        ObservableList<Contact> allContacts = sqlCon.getContactList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contact c : allContacts) {
            contactNames.add(c.getName());
        }
        apptContactField.setItems(contactNames);
        if(modifiedAppt != null) {
            for(Contact c : allContacts) {
                if(c.getId() == modifiedAppt.getId()) {
                    apptContactField.setPromptText(c.getName());
                }
            }
        } else {
            System.out.println("new");
        }
    }
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

    public void onSaveAppt(ActionEvent actionEvent) {

    }
}
