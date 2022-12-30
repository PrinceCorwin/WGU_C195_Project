package controller;

import classes.Appt;
import classes.Contact;
import classes.Customer;
import classes.User;
import databaseHelp.sqlCon;
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

public class AddUpdateApptsController {
    ObservableList<Contact> allContacts = sqlCon.getContactList();

    boolean errors = false;

    public TextField apptIdField;
    public TextField apptTitleField;
    public TextField apptTypeField;
    public TextField apptLocField;
    public TextField apptDescField;
    public ComboBox<Integer> apptCustIdField;
    public ComboBox<Integer> apptUserIdField;
    public ComboBox<String> apptContactField;
    public TextField apptEndDateField;
    public TextField apptEndTimeField;
    public TextField apptStartDateField;
    public TextField apptStartTimeField;
    public Label apptFormTitle;

    public String splitDateTime(String dateStr, String dateOrTime) {
        String[] splitDate = dateStr.split(" ", 2);
        if (Objects.equals(dateOrTime, "time")) {
            return splitDate[1];
        } else {
            return splitDate[0];
        }
    }

    public void initialize() {
//        ObservableList<Appt> allAppts =  FXCollections.observableArrayList();
//        ObservableList<Contact> allContacts = sqlCon.getContactList();
        ObservableList<Customer> allCustomers = sqlCon.getCustomerList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<Integer> custIds = FXCollections.observableArrayList();
        ObservableList<Integer> allUserIds = sqlCon.getUserIds();

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
//            apptCustIdField.setText(modifiedAppt.getId().toString());
            apptTypeField.setText(modifiedAppt.getType());
//            apptUserIdField.setText(modifiedAppt.getUserId().toString());
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

    private int getUniqueId() {
        int count = 0;
        ObservableList<Appt> appts = sqlCon.getApptList();
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

    public void onSaveAppt(ActionEvent actionEvent) throws IOException {
        String startTime = verifyDateFormat(apptStartTimeField.getText());
        String endTime = verifyTimeFormat(apptEndTimeField.getText());
        verifyOverlap(startTime, endTime);
        String end = apptEndDateField.getText() + " " + endTime;
        String start = apptStartDateField.getText() + " " + startTime;
        int userId = 0;
        String contact = "";
        int custId = 0;
//        if (checkForInt(apptUserIdField.getText())) {
//            userId = Integer.parseInt(apptUserIdField.getText());
//        }  else {
//// set error label text
//            errors = true;
//        }
        String title = apptTitleField.getText();
        String desc = apptDescField.getText();
        String loc = apptLocField.getText();
        String type = apptTypeField.getText();
        String userName = User.getUserName();
        if (checkForSelect()) {
            contact = apptContactField.getValue();
            custId = apptCustIdField.getValue();
            userId = apptUserIdField.getValue();
        } else {
//            set error label text
            errors = true;
        }

        int contactId = 0;
        int id = parseInt(apptIdField.getText());
//        ObservableList<Contact> allContacts = sqlCon.getContactList();
        for (Contact c : allContacts) {
            if (Objects.equals(c.getName(), contact)) {
                contactId = c.getId();
            }
        }
        if(!errors) {
            try {
                String custQuery;
                if (modifiedAppt == null) {
                    custQuery = String.format("INSERT INTO appointments VALUES(%d, '%s', '%s', '%s', '%s'," +
                                    " '%s', '%s', NOW(), '%s', NOW(), '%s', %d, %d, %d)",
                            id, title, desc, loc, type, start, end, userName, userName, custId, userId, contactId);
                } else {
                    custQuery = String.format("UPDATE appointments SET Title = '%s', Description = '%s', Location = '%s', Type = '%s'," +
                            "Start ='%s', End = '%s', Last_Update = NOW()," +
                            "Last_Updated_By = '%s', Customer_ID = %d, User_ID = %d, Contact_ID = %d WHERE Appointment_ID = %d", title, desc, loc, type, start, end, userName, custId, userId, contactId, id);
                }
                PreparedStatement myPs = sqlCon.getConnection().prepareStatement(custQuery);
                myPs.executeUpdate();
                modifiedAppt = null;
                backToMain(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkForSelect() {
        if (apptCustIdField.getSelectionModel().isEmpty()) {
//            set error label
            return false;
        }
        if (apptContactField.getSelectionModel().isEmpty()) {
//            set error label
            return false;
        }
        if (apptUserIdField.getSelectionModel().isEmpty()) {
//            set error label
            return false;
        }
        System.out.println("selected");
        return true;
    }

    private void verifyOverlap(String startTime, String endTime) {
//        verify overlap and errors = true if wrong. set error label
    }

    private String verifyTimeFormat(String text) {
//        verify format and errors = true if wrong set error label
        return text;
    }

    private String verifyDateFormat(String text) {
//        verify format and errors =  if wrong. set error label
        return text;
    }

    private boolean checkForInt(String str) {
        try {
            Integer.parseInt(str);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
