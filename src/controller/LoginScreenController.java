package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
