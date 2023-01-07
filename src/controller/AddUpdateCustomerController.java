package controller;

import Interfaces.SetStageInterface;
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
 * Controls the behavior of the AddUpdateCustomer.fxml scene. Provides functionality to either
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
     * lambda expression to change current scene to a new scene. Used to clean up repeated code.
     * @param actionEvent the action event
     * @param path the path of the fxml file of the scene
     * @param width the width of the scene
     * @param height the height of the scene
     */
    SetStageInterface basicStage = (ActionEvent actionEvent, String path, float width, float height) -> {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    };

    /**
     * Sets modifiedCust variable to selected Customer to be modified from the mainForm controller.
     * @param customer the Customer to set
     */
    public static void setModifiedCust(Customer customer) {
        modifiedCust = customer;
    }

    /**
     * Changes the current scene back to the MainForm.fxml.
     * lambda expression used here to change current scene to MainForm.fxml
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onCustCancel(ActionEvent actionEvent) throws IOException {
        modifiedCust = null;
        basicStage.setStage(actionEvent, "/fxml/MainForm.fxml", 1200, 600);
    }


    /**
     * New or updated Customer is either saved to database or an error message is displayed for user to correct.
     * Checks to verify all combobox selections have been made.
     * lambda expression used here to change current scene to MainForm.fxml upon successful save.
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
                    basicStage.setStage(actionEvent, "/fxml/mainForm.fxml", 1200, 600);
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
