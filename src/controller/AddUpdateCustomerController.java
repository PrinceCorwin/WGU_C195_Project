package controller;

import classes.Appt;
import classes.Contact;
import classes.Customer;
import databaseHelp.Helper;
import databaseHelp.SqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddUpdateCustomerController {
    private static Customer modifiedCust = null;
    public Label custFormTitle;
    public TextField custNameField;
    public TextField custPhoneField;
    public TextField custAddressField;
    public ComboBox<String> custCountryBox;
    public ComboBox<String> custStateBox;
    public Label custErrorLabel;
    public TextField custIdField;
    public TextField custZipField;

    public void initialize() {
        ObservableList<Customer> allCustomers = SqlCon.getCustomerList();
        ObservableList<Integer> custIds = FXCollections.observableArrayList();

        if(modifiedCust != null) {

            custIdField.setText(String.valueOf(modifiedCust.getId()));
            custFormTitle.setText("UPDATE CUSTOMER");
            custAddressField.setText(modifiedCust.getAddress());
            custZipField.setText(modifiedCust.getZip());
            custPhoneField.setText(modifiedCust.getPhone());
            custNameField.setText(modifiedCust.getName());
            custCountryBox.setValue(modifiedCust.getCountry());
            custStateBox.setValue(modifiedCust.getState());

        } else {
            custIdField.setText(String.valueOf(getUniqueId()));
        }
    }
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

    public void onCustSave(ActionEvent actionEvent) {
    }
    public static int getUniqueId() {
        int count = 0;
        ObservableList<Customer> allCustomers = SqlCon.getCustomerList();
        boolean unique;
        do {
            unique = true;
            count++;
            for (Customer a : allCustomers) {
                if (a.getId() == count) {
                    unique = false;
                }
            }
        } while (!unique);
        return count;
    }
}
