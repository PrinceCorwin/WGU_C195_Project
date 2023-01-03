package controller;

import classes.*;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

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
    int divId;

    public void initialize() {
        ObservableList<Customer> allCustomers = SqlCon.getCustomerList();
        ObservableList<Country> allCountries = SqlCon.getCountryList();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
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
            allCountries.forEach( (c) -> {countryNames.add(c.getName());});
            custCountryBox.setItems(countryNames);
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

    public void onCustSave(ActionEvent actionEvent) throws IOException{
        int id = parseInt(custIdField.getText());
        String name = custNameField.getText();
        String address = custAddressField.getText();
        String zip = custZipField.getText();
        String phone = custPhoneField.getText();
        String userName = User.getUserName();

            try {
                String custQuery;
                if (modifiedCust == null) {
                    custQuery = String.format("INSERT INTO customers VALUES(%d, '%s', '%s', '%s', '%s', NOW(), '%s'," +
                                    "NOW(), '%s', %d)",
                            id, name, address, zip, phone, userName, userName, divId);
                } else {
                    setDivId();
                    custQuery = String.format("UPDATE customers SET Customer_Name = '%s', Address = '%s', Postal_Code = '%s', Phone = '%s'," +
                                    "Last_Update = NOW(), Last_Updated_By = '%s', Division_ID = %d WHERE Customer_ID = %d",
                            name, address, zip, phone, userName, divId, id);
                }
                PreparedStatement myPs = SqlCon.getConnection().prepareStatement(custQuery);
                myPs.executeUpdate();
                modifiedCust = null;
                backToMain(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
                    break;
                }
            }
        } while (!unique);
        return count;
    }

    public void setDivisions() {
        custStateBox.setDisable(false);
        int countryId = SqlCon.getCountryIdFromName(custCountryBox.getValue());
        ObservableList<Division> allDivisions = SqlCon.getDivisionList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for(Division d : allDivisions) {
            if (d.getCountryId() == countryId) {
                divisionNames.add(d.getName());
            }
        }
        custStateBox.setItems(divisionNames);
    }

    public void setDivId() {
        divId = SqlCon.getDivIdFromName(custStateBox.getValue());
    }
}
