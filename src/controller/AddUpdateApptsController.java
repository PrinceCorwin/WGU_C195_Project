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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class AddUpdateApptsController {
    public TextField apptIdField;
    public TextField apptTitleField;
    public TextField apptTypeField;
    public TextField apptLocField;
    public TextField apptDescField;
    public TextField apptCustIdField;
    public TextField apptUserIdField;
    public ComboBox<String> apptContactField;
    public TextField apptEndDateField;
    public TextField apptEndTimeField;
    public TextField apptStartDateField;
    public TextField apptStartTimeField;
    public Label apptFormTitle;

    public void initialize() {
        ObservableList<Appt> allAppts =  FXCollections.observableArrayList();
        ObservableList<Contact> allContacts = sqlCon.getContactList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contact c : allContacts) {
            contactNames.add(c.getName());
        }
        apptContactField.setItems(contactNames);
        if(modifiedAppt != null) {
            for(Contact c : allContacts) {
                if(c.getId() == modifiedAppt.getId()) {
                    apptContactField.setValue(c.getName());
                }
            }
            apptFormTitle.setText("UPDATE APPOINTMENT");
            apptDescField.setText(modifiedAppt.getDesc());
            apptTitleField.setText(modifiedAppt.getTitle());
            apptLocField.setText(modifiedAppt.getLoc());
            apptCustIdField.setText(modifiedAppt.getId().toString());
            apptTypeField.setText(modifiedAppt.getType());
            apptUserIdField.setText(modifiedAppt.getUserId().toString());
            apptIdField.setText(modifiedAppt.getId().toString());
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
        boolean unique = false;
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
    public void onSaveAppt(ActionEvent actionEvent) {
        if (modifiedAppt == null) {
            String createdBy;
            String create;
        }
        String title = apptTitleField.getText();
        String desc = apptDescField.getText();
        String loc = apptLocField.getText();
        String type = apptTypeField.getText();
        String end = apptEndDateField.getText();
        String start = apptStartDateField.getText();
        String update;
        String updatedBy;
        int custId = parseInt(apptCustIdField.getText());
        int userId = parseInt(apptUserIdField.getText());
        String contact = apptContactField.getValue();
        System.out.println(contact);
        int id = parseInt(apptIdField.getText());
        System.out.println(id);
        try {
            String custQuery = String.format("INSERT INTO appointments VALUES(%d, '%s', '%s', '%s', 'De-Briefing', '2020-05-29 12:00:00', '2020-05-29 13:00:00', NOW(), 'script', NOW(), 'script', 2, 2, 2)", id, title, desc, loc);
//            INSERT INTO appointments VALUES(%d, '%s', '%s', '%s', 'De-Briefing', '2020-05-29 12:00:00', '2020-05-29 13:00:00', NOW(), 'script', NOW(), 'script', 2, 2, 2)
//            INSERT INTO appointments VALUES(2, 'title', 'description', 'location', 'De-Briefing', '2020-05-29 12:00:00', '2020-05-29 13:00:00', NOW(), 'script', NOW(), 'script', 2, 2, 2)
            PreparedStatement myPs = sqlCon.getConnection().prepareStatement(custQuery);
            myPs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
