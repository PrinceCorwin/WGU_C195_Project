package controller;

import Interfaces.SetStageInterface;
import classes.Appt;
import classes.Customer;
import databaseHelp.SqlCon;
import javafx.application.Platform;
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
import java.util.*;

/**
 * Controls the behavior of the mainForm.fxml scene. Displays the custTable and apptTable in a tab pane with buttons
 * for add, update, delete, reports, and exit
 * lambda expression used here for the sake of abstraction and cleaner code to SetStageInterface basicStage variable is
 * defined here for use in other methods to change scenes.
 * @author Steve Corwin Amalfitano
 */
public class MainFormController {

    /**
     * Initializes the apptTable and custTable to accept data and populates them with data from database
     */
    public void initialize() {
        apptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptLoc.setCellValueFactory(new PropertyValueFactory<>("loc"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        custId.setCellValueFactory(new PropertyValueFactory<>("id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        custCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        custState.setCellValueFactory(new PropertyValueFactory<>("state"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        custCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        custLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        custUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        custDivId.setCellValueFactory(new PropertyValueFactory<>("divId"));

        apptTable.setItems(appts);
        custTable.setItems(customers);
    }

    public TableView<Appt> apptTable;
    public TableColumn<Appt, Integer> apptId;
    public TableColumn<Appt, String> apptTitle;
    public TableColumn<Appt, String> apptDesc;
    public TableColumn<Appt, Integer> apptContact;
    public TableColumn<Appt, String> apptLoc;
    public TableColumn<Appt, String> apptType;
    public TableColumn<Appt, String > apptStart;
    public TableColumn<Appt, String > apptEnd;
    public TableColumn<Appt, Integer> apptCustId;
    public TableColumn<Appt, Integer> apptUserId;
    public Label selectApptError;
    public TableView<Customer> custTable;
    public TableColumn<Customer, Integer> custId;
    public TableColumn<Customer, String> custName;
    public TableColumn<Customer, String > custAddress;
    public TableColumn<Customer, String> custZip;
    public TableColumn<Customer, String> custCountry;
    public TableColumn<Customer, String> custState;
    public TableColumn<Customer, String> custPhone;
    public TableColumn<Customer, String > custCreateDate;
    public TableColumn<Customer, String> custCreatedBy;
    public TableColumn<Customer, String > custLastUpdate;
    public TableColumn<Customer, String> custUpdatedBy;
    public TableColumn<Customer, Integer> custDivId;
    public Label selectCustError;
    public RadioButton allApptView;
    public ToggleGroup radioToggle;
    public RadioButton weekApptView;
    public RadioButton monthApptView;
    private ObservableList<Customer> customers = SqlCon.getCustomerList();
    private ObservableList<Appt> appts = SqlCon.getApptList("all");

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
     * Changes scene to the addUpdateCustomer form to allow creation of a new Customer
     * basicStage.setStage() as defined in lambda expression above used here to change scenes
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onAddCust(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/AddUpdateCustomer.fxml", 600, 450);
    }

    /**
     * Determines if a Customer is selected and, if so, changes scene to addUpdateCustomer.fxml. If no Customer selected,
     * an error message appears instructing user to select a Customer
     * basicStage.setStage() as defined in lambda expression above used here to change scenes
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onUpdateCust(ActionEvent actionEvent) throws IOException {
        boolean nullPointer = isCustSelected();

        if (!nullPointer) {
            Customer customer = custTable.getSelectionModel().getSelectedItem();
            AddUpdateCustomerController.setModifiedCust(customer);
            basicStage.setStage(actionEvent, "/fxml/AddUpdateCustomer.fxml", 600, 450);
        }

    }

    /**
     * Deletes the selected customer from the database. Shows alert to allow user to verify intention to delete. If no
     * Customer is selected, an error message appears telling user to select a customer
     */
    public void onDeleteCust() {
        boolean error = false;
        boolean nullPointer = isCustSelected();
        if (!nullPointer) {
            Customer deletedCust = custTable.getSelectionModel().getSelectedItem();
            int deletedId = deletedCust.getId();
            ObservableList<Appt> allAppts = SqlCon.getApptList("all");
            for (Appt a : allAppts) {
                if (a.getCustId() == deletedId) {
                    selectCustError.setVisible(true);
                    selectCustError.setText(String.format("Delete all %s\'s Appointments before deleting", deletedCust.getName()));
                    System.out.println("error");
                    error = true;
                    break;
                }
            }

            if (!error) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Delete " + deletedCust.getName());
                alert.setContentText("Are you sure?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    SqlCon.deleteCust(deletedCust);
                    custTable.setItems(SqlCon.getCustomerList());
                }
            }
        }
    }

    /**
     * Changes scene to the addUpdateAppt form to allow creation of a new Appt
     * basicStage.setStage() as defined in lambda expression above used here to change scenes
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/AddUpdateAppts.fxml", 600, 500);
    }

    /**
     * Changes scene to the addUpdateAppt form to allow updating of an existing Appt. If no Appt is selected, error
     * message appears instructing user to select an Appt
     * basicStage.setStage() as defined in lambda expression above used here to change scenes
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onUpdateAppt(ActionEvent actionEvent) throws IOException {
        boolean nullPointer = isApptSelected();

        if (!nullPointer) {
            Appt appt = apptTable.getSelectionModel().getSelectedItem();
            AddUpdateApptsController.setModifiedAppt(appt);
            basicStage.setStage(actionEvent, "/fxml/AddUpdateAppts.fxml", 600, 500);
        }
    }

    /**
     * Deletes the selected Appt from the database. Shows alert to allow user to verify intention to delete. If no
     * Appt is selected, an error message appears telling user to select an Appt
     */
    public void onDeleteAppt() {
       boolean nullPointer = isApptSelected();
        if (!nullPointer) {
            Appt deletedAppt = apptTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete " + deletedAppt.getType() + " at " + deletedAppt.getStart());
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                SqlCon.deleteAppt(deletedAppt);
                String view = "all";
                if (weekApptView.isSelected()) {
                    view = "week";
                } else if (monthApptView.isSelected()) {
                    view = "month";
                }
                apptTable.setItems(SqlCon.getApptList(view));
            }
        }
    }

    /**
     * Filters the apptTable appointments to show all appointments
     */
    public void onAllApptView() {
        apptTable.setItems(appts);
    }

    /**
     * Filters the apptTable appointments to show only appointments within the current week.
     */
    public void onWeekApptView() {
        ObservableList<Appt> weekly = SqlCon.getApptList("week");
        apptTable.setItems(weekly);

    }

    /**
     * Filters the apptTable appointments to show only appointments within the current month.
     */
    public void onMonthApptView() {
        ObservableList<Appt> monthly = SqlCon.getApptList("month");
        apptTable.setItems(monthly);
    }

    /** Determines if an Appt is selected when either the update or delete buttons are clicked
     * @return true if an Appt is selected, otherwise return false
     */
    public boolean isApptSelected() {
        selectApptError.setVisible(false);
        boolean nullPointer = true;
        Appt appt = apptTable.getSelectionModel().getSelectedItem();
        try {
            if (appt.getTitle() != null) {
                nullPointer = false;
            }
        }
        catch (NullPointerException e) {
            selectApptError.setVisible(true);
        }
        return nullPointer;
    }

    /** Determines if a Customer is selected when either the update or delete buttons are clicked
     * @return true if a Customer is selected, otherwise return false
     */
    public boolean isCustSelected() {
        selectCustError.setVisible(false);
        boolean nullPointer = true;
        Customer cust = custTable.getSelectionModel().getSelectedItem();
        try {
            if (cust.getName() != null) {
                nullPointer = false;
            }
        }
        catch (NullPointerException e) {
            selectCustError.setVisible(true);
        }
        return nullPointer;
    }

    /**
     * lambda expression is used to change scene to Reports.fxml.
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onReports(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/Reports.fxml", 1200, 600);
    }

    /**
     * exits program.
     */
    public void onExit() {
        Platform.exit();
    }
}