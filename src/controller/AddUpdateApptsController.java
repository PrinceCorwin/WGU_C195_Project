package controller;

import classes.Appt;
import classes.Contact;
import classes.Customer;
import classes.User;
import databaseHelp.SqlCon;
import databaseHelp.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class AddUpdateApptsController {

    ObservableList<Contact> allContacts = SqlCon.getContactList();

    public Label errorLabel;
    public TextField apptIdField;
    public TextField apptTitleField;
    public TextField apptTypeField;
    public TextField apptLocField;
    public TextArea apptDescField;
    public ComboBox<Integer> apptCustIdField;
    public ComboBox<Integer> apptUserIdField;
    public ComboBox<String> apptContactField;
    public TextField apptEndDateField;
    public TextField apptEndTimeField;
    public TextField apptStartDateField;
    public TextField apptStartTimeField;
    public Label apptFormTitle;

    public void initialize() {

        ObservableList<Customer> allCustomers = SqlCon.getCustomerList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<Integer> custIds = FXCollections.observableArrayList();
        ObservableList<Integer> allUserIds = SqlCon.getUserIds();

        for(Contact c : allContacts) {
            contactNames.add(c.getName());
        }
        for(Customer c : allCustomers) {
            custIds.add(c.getId());
        }

        apptContactField.setItems(contactNames);
        apptCustIdField.setItems(custIds);
        apptUserIdField.setItems(allUserIds);
        if(modifiedAppt != null) {
            for(Contact c : allContacts) {
                if(c.getId() == modifiedAppt.getContact()) {
                    apptContactField.setValue(c.getName());
                }
            }
            for(Customer c : allCustomers) {
                if(c.getId() == modifiedAppt.getCustId()) {
                    apptCustIdField.setValue(c.getId());
                }
            }
            for(Integer i : allUserIds) {
                if(Objects.equals(i, modifiedAppt.getUserId())) {
                    apptUserIdField.setValue(i);
                }
            }
            apptFormTitle.setText("UPDATE APPOINTMENT");
            apptDescField.setText(modifiedAppt.getDesc());
            apptTitleField.setText(modifiedAppt.getTitle());
            apptLocField.setText(modifiedAppt.getLoc());
            apptTypeField.setText(modifiedAppt.getType());
            apptIdField.setText(modifiedAppt.getId().toString());
            apptStartDateField.setText(splitDateTime((modifiedAppt.getStart()), "date"));
            apptEndDateField.setText(splitDateTime((modifiedAppt.getEnd()), "date"));
            apptEndTimeField.setText(splitDateTime((modifiedAppt.getEnd()), "time"));
            apptStartTimeField.setText(splitDateTime((modifiedAppt.getStart()), "time"));
        } else {
            apptIdField.setText(String.valueOf(getUniqueId()));
        }
    }
    private static Appt modifiedAppt = null;
    public static void setModifiedAppt(Appt appt) {
        modifiedAppt = appt;
    }

    public void onApptCancel(ActionEvent actionEvent) throws IOException {
        hideErrors();
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

    public void onSaveAppt(ActionEvent actionEvent) throws IOException {
        boolean errors = false;
        String startTime;
        String endTime;
        String end = null;
        String start = null;
        String title = apptTitleField.getText();
        String desc = apptDescField.getText();
        String loc = apptLocField.getText();
        String type = apptTypeField.getText();
        String userName = User.getUserName();
        int userId = 0;
        String contact;
        int custId = 0;
        int contactId = 0;
        int id = parseInt(apptIdField.getText());

        if (Helper.verifyTimeFormat(apptStartTimeField.getText()) && Helper.verifyTimeFormat(apptEndTimeField.getText())
                && Helper.verifyDateFormat(apptEndDateField.getText()) && Helper.verifyDateFormat(apptStartDateField.getText())) {
            startTime = apptStartTimeField.getText();
            endTime = apptEndTimeField.getText();
            end = Helper.localToUTC(apptEndDateField.getText() + " " + endTime);
            start = Helper.localToUTC(apptStartDateField.getText() + " " + startTime);
            ComboBox[] comboArray = {apptContactField, apptCustIdField, apptUserIdField};
            if (checkForSelect(comboArray)) {
                contact = apptContactField.getValue();
                custId = apptCustIdField.getValue();
                userId = apptUserIdField.getValue();
                for (Contact c : allContacts) {
                    if (Objects.equals(c.getName(), contact)) {
                        contactId = c.getId();
                    }
                }
                if (!verifyTimeAvailable(start, end, startTime, endTime, custId)) {
                    errors = true;
                }
            } else {
                errorLabel.setText("Error: Please select an option in each of the drop-down combo boxes");
                errors = true;
            }
        } else {
            errors = true;
            errorLabel.setText("Error: Date must be entered in yyyy-MM-dd format. \n       Time must be entered in HH:mm:ss format");
        }


        if(!errors) {
            String lastUpdated = Helper.getCurrentUtcTime();
            lastUpdated = Helper.localToUTC(lastUpdated);
            try {
                String custQuery;
                if (modifiedAppt == null) {
                    custQuery = String.format("INSERT INTO appointments VALUES(%d, '%s', '%s', '%s', '%s'," +
                                    " '%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d)",
                            id, title, desc, loc, type, start, end, lastUpdated, userName, lastUpdated, userName, custId, userId, contactId);
                } else {
                    custQuery = String.format("UPDATE appointments SET Title = '%s', Description = '%s', Location = '%s', Type = '%s'," +
                            "Start ='%s', End = '%s', Last_Update = '%s'," +
                            "Last_Updated_By = '%s', Customer_ID = %d, User_ID = %d, Contact_ID = %d WHERE Appointment_ID = %d",
                            title, desc, loc, type, start, end, lastUpdated, userName, custId, userId, contactId, id);
                }
                PreparedStatement myPs = SqlCon.getConnection().prepareStatement(custQuery);
                myPs.executeUpdate();
                modifiedAppt = null;
                hideErrors();
                backToMain(actionEvent);
            } catch (SQLException e) {
            }
        }
    }

    private void hideErrors() {
        errorLabel.setText("");
    }

    private boolean checkForSelect(ComboBox[] comboArray) {
        for (ComboBox c : comboArray) {
            if (c.getSelectionModel().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyTimeAvailable(String start, String end, String startTime, String endTime, int custId) {
        if (!SqlCon.verifyOverlap(start, end, custId)) {
            errorLabel.setText("Error: Appointment time overlaps another appointment \n       with the same customer");
            return false;
        }
        if (!Helper.verifyBusHours(startTime, endTime)) {
            errorLabel.setText("Error: Appointment time is outside of business hours" +
                    " \n       Business hours: (08:00:00 - 22:00:00 EST)");
            return false;
        }
        return true;
    }
    public String splitDateTime(String dateStr, String dateOrTime) {
        String[] splitDate = dateStr.split(" ", 2);
        if (Objects.equals(dateOrTime, "time")) {
            return splitDate[1];
        } else {
            return splitDate[0];
        }
    }
    public static int getUniqueId() {
        int count = 0;
        ObservableList<Appt> appts = SqlCon.getApptList("all");
        boolean unique;
        do {
            unique = true;
            count++;
            for (Appt a : appts) {
                if (a.getId() == count) {
                    unique = false;
                }
            }
        } while (!unique);
        return count;
    }
}
