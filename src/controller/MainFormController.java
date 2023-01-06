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

public class MainFormController {

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

    SetStageInterface basicStage = (actionEvent, path, width, height) -> {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    };
    public void onAddCust(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/AddUpdateCustomer.fxml", 600, 450);
    }
    /**
     * Upon button click, scene is replaced by the addUpdateCustomer.fxml scene and the selected Customer is stored in the
     * addUpdateCustomer to be modified
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

    public void onDeleteCust(ActionEvent actionEvent) {
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

    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/AddUpdateAppts.fxml", 600, 500);
    }

    public void onUpdateAppt(ActionEvent actionEvent) throws IOException {
        boolean nullPointer = isApptSelected();

        if (!nullPointer) {
            Appt appt = apptTable.getSelectionModel().getSelectedItem();
            AddUpdateApptsController.setModifiedAppt(appt);
            basicStage.setStage(actionEvent, "/fxml/AddUpdateAppts.fxml", 600, 500);
        }
    }

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

    public void onAllApptView(ActionEvent actionEvent) {
        apptTable.setItems(appts);
    }

    public void onWeekApptView(ActionEvent actionEvent) {
        ObservableList<Appt> weekly = SqlCon.getApptList("week");
        apptTable.setItems(weekly);

    }

    public void onMonthApptView(ActionEvent actionEvent) {
        ObservableList<Appt> monthly = SqlCon.getApptList("month");
        apptTable.setItems(monthly);
    }
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

    public void onReports(ActionEvent actionEvent) throws IOException {
        basicStage.setStage(actionEvent, "/fxml/Reports.fxml", 1200, 600);

    }
    public void onExit() {
        Platform.exit();
    }
}