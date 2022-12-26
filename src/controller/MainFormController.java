package controller;

import classes.Appt;
import classes.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainFormController {
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
    private ObservableList<Customer> customers = Customer.getCustomerList();
    private ObservableList<Appt> appts = Appt.getApptList();
    public void onAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddUpdateCustomer.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Upon button click, scene is replaced by the addPart.fxml scene and the selected part is stored in the
     * addPartController to be modified
     * @param actionEvent the action event
     * @throws IOException Catches any exceptions thrown during data input / output
     */
    public void onUpdateCust(ActionEvent actionEvent) throws IOException {
        selectCustError.setVisible(false);
        boolean nullPointer = true;
        Customer modCust = custTable.getSelectionModel().getSelectedItem();
        try {
            if (modCust.getName() != null) {
                nullPointer = false;
            }
//            String thisName = modPart.getName();
//            nullPointer = false;`
        }
        catch (NullPointerException e) {
            selectCustError.setVisible(true);
        }

        if (!nullPointer) {
            Customer customer = custTable.getSelectionModel().getSelectedItem();
            AddUpdateCustomerController.setModifiedCust(customer);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddUpdateCustomer.fxml")));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 700);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void onDeleteCust(ActionEvent actionEvent) {
    }

    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddUpdateAppts.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateAppt(ActionEvent actionEvent) throws IOException {
        selectApptError.setVisible(false);
        boolean nullPointer = true;
        Appt modAppt = (Appt) apptTable.getSelectionModel().getSelectedItem();
        try {
            if (modAppt.getTitle() != null) {
                nullPointer = false;
            }
//            String thisName = modPart.getName();
//            nullPointer = false;
        }
        catch (NullPointerException e) {
            selectApptError.setVisible(true);
        }

        if (!nullPointer) {
            Appt appt = apptTable.getSelectionModel().getSelectedItem();
            AddUpdateApptsController.setModifiedAppt(appt);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddUpdateAppts.fxml")));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 700);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onDeleteAppt(ActionEvent actionEvent) {
    }
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
        custCreateDate.setCellValueFactory(new PropertyValueFactory<>("create"));
        custCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        custLastUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        custUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        custDivId.setCellValueFactory(new PropertyValueFactory<>("divId"));

        apptTable.setItems(appts);
        custTable.setItems(customers);
    }
}