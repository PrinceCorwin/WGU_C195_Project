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

//        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-uuuu HH:mm:ss" ) ;
//        LocalDateTime ldt = LocalDateTime.parse( "31-12-2018 23:37:05" , f ) ;
//        String str = ZonedDateTime.now(
//                ZoneId.systemDefault()
//        ).getZone().toString();
//        System.out.println(str);
//        ZoneId z = ZoneId.of(str) ;
//        ZonedDateTime zdt = ldt.atZone( z );
//        OffsetDateTime odt = ldt.atOffset( ZoneOffset.UTC );
//        System.out.println(odt);
//
////        local to UTC
//        String datesToConvert = "31-12-2018 23:37:00";
//        String dateFormat = "dd-MM-yyyy HH:mm:ss";
//
//        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
//        sdf2.setTimeZone(TimeZone.getDefault());
//        Date gmt = null;
//
//        SimpleDateFormat sdfOutPutToSend = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        sdfOutPutToSend.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        try {
//            gmt = sdf2.parse(datesToConvert);
//            System.out.println("UTC FORMATTED DATE : " + sdfOutPutToSend.format(gmt));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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
