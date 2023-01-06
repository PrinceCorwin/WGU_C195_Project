package controller;

import classes.*;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

/**
 * Controls the behavior of the addUpdateCustomer.fxml scene. Provides functionality to either
 * add new Customer or modify existing Customer, based on user input to the displayed form.
 * @author Steve Corwin Amalfitano
 */
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
    boolean errors = false;

    /**
     * Initializes the form with existing data if Customer has
     * been selected and modify button clicked on mainForm.
     * Also initializes the custTable to be ready to accept data.
     */
    public void initialize() {
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

    /**
     * Sets modifiedCust variable to selected Customer to be modified from the mainForm controller.
     * @param customer the Customer to set
     */
    public static void setModifiedCust(Customer customer) {
        modifiedCust = customer;
    }

    /**
     * Changes the current scene back to the mainForm.fxml by calling backToMain() when cancel button is clicked.
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onCustCancel(ActionEvent actionEvent) throws IOException {
        modifiedCust = null;
        backToMain(actionEvent);
    }

    /**
     * replaces current scene with the mainForm.fxml scene
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    private void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Checks to verify all combobox selections have been made.
     * New Customer is either saved to database or an error
     * message is displayed for user to correct. backToMain() is called upon successful save
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onCustSave(ActionEvent actionEvent) throws IOException{
        errors = false;
        custErrorLabel.setVisible(false);
        int id = parseInt(custIdField.getText());
        String name = custNameField.getText();
        String address = custAddressField.getText();
        String zip = custZipField.getText();
        String phone = custPhoneField.getText();
        String userName = User.getUserName();
            try {
                String custQuery = null;
                if (modifiedCust == null) {
                    ComboBox[] comboArray = {custCountryBox, custStateBox};
                    if (Helper.checkForSelect(comboArray)) {
                        divId = SqlCon.getDivIdFromName(custStateBox.getValue());
                        custQuery = String.format("INSERT INTO customers VALUES(%d, '%s', '%s', '%s', '%s', NOW(), '%s'," +
                                        "NOW(), '%s', %d)", id, name, address, zip, phone, userName, userName, divId);
                    } else {
                        custErrorLabel.setVisible(true);
                        errors = true;
                    }
                } else {
                    setDivId();
                    custQuery = String.format("UPDATE customers SET Customer_Name = '%s', Address = '%s', Postal_Code = '%s', Phone = '%s'," +
                                    "Last_Update = NOW(), Last_Updated_By = '%s', Division_ID = %d WHERE Customer_ID = %d",
                            name, address, zip, phone, userName, divId, id);
                }
                if (!errors) {
                    PreparedStatement myPs = SqlCon.getConnection().prepareStatement(custQuery);
                    myPs.executeUpdate();
                    modifiedCust = null;
                    backToMain(actionEvent);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * Iterates through integers with while loop starting at 1 and compares to existing Customer_IDs
     * to find unused integer for Customer_ID
     * @return the first unused integer found.
     */
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

    /**
     * options in the custStateBox combobox are set depending on user selection of the Country combobox.
     */
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

    /**
     * calls getDivIdFromName to get divId from first_level_division name selected
     * by user in the custStateBox
     */
    public void setDivId() {
        divId = SqlCon.getDivIdFromName(custStateBox.getValue());
    }
}
