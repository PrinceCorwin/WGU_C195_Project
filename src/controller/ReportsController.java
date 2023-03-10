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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controls the behavior of the Reports.fxml scene. Displays three reports in a tab pane.
 * @author Steve Corwin Amalfitano
 */
public class ReportsController {
    public TableView<Report> typeTable;
    public TableView<Appt> contactTable;
    public TableView<Report> userTable;
    public TableColumn<Report, String> typeType;
    public TableColumn<Report, Integer> typeJan;
    public TableColumn<Report, Integer> typeFeb;
    public TableColumn<Report, Integer> typeMar;
    public TableColumn<Report, Integer> typeApr;
    public TableColumn<Report, Integer> typeMay;
    public TableColumn<Report, Integer> typeJun;
    public TableColumn<Report, Integer> typeJul;
    public TableColumn<Report, Integer> typeAug;
    public TableColumn<Report, Integer> typeSep;
    public TableColumn<Report, Integer> typeOct;
    public TableColumn<Report, Integer> typeNov;
    public TableColumn<Report, Integer> typeDec;
    public TableColumn<Report, Integer> userId;
    public TableColumn<Report, Integer> userJan;
    public TableColumn<Report, Integer> userFeb;
    public TableColumn<Report, Integer> userMar;
    public TableColumn<Report, Integer> userApr;
    public TableColumn<Report, Integer> userMay;
    public TableColumn<Report, Integer> userJun;
    public TableColumn<Report, Integer> userJul;
    public TableColumn<Report, Integer> userAug;
    public TableColumn<Report, Integer> userSep;
    public TableColumn<Report, Integer> userOct;
    public TableColumn<Report, Integer> userNov;
    public TableColumn<Report, Integer> userDec;
    public TableColumn<Appt, Integer> apptId;
    public TableColumn<Appt, String> apptTitle;
    public TableColumn<Appt, String> apptType;
    public TableColumn<Appt, String> apptDesc;
    public TableColumn<Appt, String> apptStart;
    public TableColumn<Appt, String> apptEnd;
    public TableColumn<Appt, Integer> apptCustId;
    public ComboBox<Integer> chooseContactBtn;
    public Label contactNameLabel;

    private ObservableList<Appt> appts = SqlCon.getApptList("all");
    private ObservableList<Report> typeReport = FXCollections.observableArrayList();
    private ObservableList<Report> userReport = FXCollections.observableArrayList();
    private ObservableList<Integer> userIds = SqlCon.getUserIds();
    private ArrayList<String> allTypes = SqlCon.getAllTypes();

    /**
     * Initializes the tables in the three reports to accept data and populates two of the reports by calling getReports().
     * The third report is populated after user selects and Contact via the combobox provided
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void initialize() throws IOException {
        ObservableList<Contact> allContacts = SqlCon.getContactList();
        ObservableList<Integer> contactIds = FXCollections.observableArrayList();
        for(Contact c : allContacts) {
            contactIds.add(c.getId());
            }
        chooseContactBtn.setItems(contactIds);

        apptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));

        typeType.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeJan.setCellValueFactory(new PropertyValueFactory<>("jan"));
        typeFeb.setCellValueFactory(new PropertyValueFactory<>("feb"));
        typeMar.setCellValueFactory(new PropertyValueFactory<>("mar"));
        typeApr.setCellValueFactory(new PropertyValueFactory<>("apr"));
        typeMay.setCellValueFactory(new PropertyValueFactory<>("may"));
        typeJun.setCellValueFactory(new PropertyValueFactory<>("jun"));
        typeJul.setCellValueFactory(new PropertyValueFactory<>("jul"));
        typeAug.setCellValueFactory(new PropertyValueFactory<>("aug"));
        typeSep.setCellValueFactory(new PropertyValueFactory<>("sep"));
        typeOct.setCellValueFactory(new PropertyValueFactory<>("oct"));
        typeNov.setCellValueFactory(new PropertyValueFactory<>("nov"));
        typeDec.setCellValueFactory(new PropertyValueFactory<>("dec"));

        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userJan.setCellValueFactory(new PropertyValueFactory<>("jan"));
        userFeb.setCellValueFactory(new PropertyValueFactory<>("feb"));
        userMar.setCellValueFactory(new PropertyValueFactory<>("mar"));
        userApr.setCellValueFactory(new PropertyValueFactory<>("apr"));
        userMay.setCellValueFactory(new PropertyValueFactory<>("may"));
        userJun.setCellValueFactory(new PropertyValueFactory<>("jun"));
        userJul.setCellValueFactory(new PropertyValueFactory<>("jul"));
        userAug.setCellValueFactory(new PropertyValueFactory<>("aug"));
        userSep.setCellValueFactory(new PropertyValueFactory<>("sep"));
        userOct.setCellValueFactory(new PropertyValueFactory<>("oct"));
        userNov.setCellValueFactory(new PropertyValueFactory<>("nov"));
        userDec.setCellValueFactory(new PropertyValueFactory<>("dec"));
        getReports();

    }

    /**
     * Fetches the data for two of the reports and returns the data to the initializer to populate tables
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    private void getReports() throws IOException {
        for (Integer id : userIds) {
            int jan = 0, feb = 0, mar = 0, apr = 0, may = 0, jun = 0, jul = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
            int[] months = {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};
            for (int i = 1; i<13; i++) {
                int num = SqlCon.getNumAppts(id, i);
                months[i-1] = num;
            }
            Report report = new Report(months[0], months[1], months[2], months[3], months[4], months[5], months[6], months[7], months[8], months[9], months[10], months[11], id, "type");
            userReport.add(report);
        }
        userTable.setItems(userReport);
        for (String type : allTypes) {
            int jan = 0, feb = 0, mar = 0, apr = 0, may = 0, jun = 0, jul = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
            int[] months = {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};
            for (int i = 1; i<13; i++) {
                int num = SqlCon.getApptsByType(type, i);
                months[i-1] = num;
            }
            Report report = new Report(months[0], months[1], months[2], months[3], months[4], months[5], months[6], months[7], months[8], months[9], months[10], months[11], 0, type);
            typeReport.add(report);
        }
        typeTable.setItems(typeReport);
    }

    /**
     * Changes scene to MainForm.fxml when the HOME button is clicked
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Fetches data from database to populate the schedule report based on user selection in the combobox
     * @param actionEvent the action event
     */
    public void setSchedule(ActionEvent actionEvent) {
        int contactId = chooseContactBtn.getValue();
        ObservableList<Appt> appts = SqlCon.getApptsByContact(contactId);
        ObservableList<Contact> contacts = SqlCon.getContactList();
        for (Contact c : contacts) {
            if (c.getId() == contactId) {
                contactNameLabel.setText(c.getName());
            }
        }
        contactTable.setItems(appts);

    }
}
