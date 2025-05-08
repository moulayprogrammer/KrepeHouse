package com.moulay.krepehouse.Controllers;

import com.moulay.krepehouse.BddPackage.UserOperation;
import com.moulay.krepehouse.Models.User;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField tfUser;
    @FXML
    PasswordField pfPass;
    @FXML
    private Label lbAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void ActionAnnular(){
        Platform.exit();
        System.exit(0);
//
    }

    @FXML
    private void ActionLogin(){
        String username = tfUser.getText().trim();
        String password = pfPass.getText().trim();

        if ( !username.isEmpty() && !password.isEmpty() ){
            User user = new User(username,password);
            login(user);
        }else {
            labelAlert("تاكد من ملا كل الخانات");
        }
    }

    private void login(User user){
        UserOperation userOperation = new UserOperation();
        boolean log = true; /* userOperation.isExist(user);*/
        if (log){
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moulay/krepehouse/hello-view.fxml"));
                TabPane temp = loader.load();
                Scene scene = new Scene(temp);
                stage.setScene(scene);
//                stage.setMaximized(true);
//                stage.getIcons().add(new Image("Icons/logo.png"));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((Stage)tfUser.getScene().getWindow()).close();
        }
        else {
            labelAlert("اسم المستخدم او كلمة السر خاطئة");
        }
    }

    private void labelAlert(String st){

        try {

            lbAlert.setText(st);
            FadeTransition ft = new FadeTransition(Duration.millis(2000), lbAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.play();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
