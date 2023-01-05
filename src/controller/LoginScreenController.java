package controller;

import classes.Appt;
import databaseHelp.Helper;
import databaseHelp.SqlCon;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoginScreenController {
    public Button loginSubmit;
    public Label loginError;
    public TextField passwordField;
    public TextField userNameField;
    public Label loginLabel;
    public Label userLocationLabel;
    public Label locationLabel;

    public void initialize() {
        Locale currentLocale = Locale.getDefault();
        String currentLocation = currentLocale.getDisplayCountry();
        String lang = currentLocale.getLanguage();
        locationLabel.setText(currentLocation);
        if (Objects.equals(lang, "fr")) {
            translateApp();
        } else {
            loginSubmit.setText("SUBMIT");
            loginError.setText("Error: Username or Password not recognized. \nPlease try again");
            userLocationLabel.setText("User Location:");
            passwordField.setPromptText("ENTER PASSWORD");
            userNameField.setPromptText("ENTER USERNAME");
            loginLabel.setText("LOGIN");
        }
    }

    private void translateApp() {
        loginSubmit.setText("SOUMETTRE");
        loginError.setText("Nom d'utilisateur ou mot de passe non reconnu. \nVeuillez réessayer");
        userLocationLabel.setText("Emplacement de l'utilisateur:");
        passwordField.setPromptText("LE MOT DE PASSE");
        userNameField.setPromptText("NOM D'UTILISATEUR");
        loginLabel.setText("CONNEXION");


    }

    public void onLoginSubmit(ActionEvent actionEvent) throws IOException {
        loginError.setVisible(false);
        String userName = userNameField.getText();
        String password = passwordField.getText();
        boolean validLogin = SqlCon.validateLogin(userName, password);
        String currentDate = Helper.getCurrentUtcTime();
        String success = (validLogin) ? "Successful" : "failed";
        String log = String.format("\n%s login attempt by '%s', on %s", success, userName, currentDate);

        try {
            FileWriter loginTracker = new FileWriter("login_activity.txt", true);
            loginTracker.write(log);
            loginTracker.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if (validLogin) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 600);
            stage.setScene(scene);
            stage.show();
            ObservableList<Appt> alerts = SqlCon.getApptList("alert");
            apptAlert(alerts);
        } else {
            loginError.setVisible(true);
            passwordField.setText("");
            userNameField.setText("");
        }
    }
    private void apptAlert(ObservableList<Appt> alerts) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String appts = "";
        alert.setTitle("Upcoming Appointments");
        if (alerts.isEmpty()) {
            alert.setHeaderText("No Appointment within 15 minutes");
        } else {
            alert.setHeaderText("Appointments within 15 minutes:");
            for (Appt a : alerts
                 ) {
                String addedStr = "Appointment ID: " + a.getId() + "  Date/Time: " + a.getStart();
                appts = appts + "\n" + addedStr;
            }
        }
        alert.setContentText(appts);
        alert.showAndWait();
    }

    public void onCancel()  {
        Platform.exit();
    }
}
