package com.moulay.krepehouse.Controllers;

import com.moulay.krepehouse.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    Tab foodTab, vendorTab,menuTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vendorTab.setContent(new TextField());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/FoodView/mainView.fxml"));
            BorderPane temp = fxmlLoader.load();
            foodTab.setContent(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    private void OnFoodTabAction(){
        deleteContent(foodTab);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/FoodView/mainView.fxml"));
            BorderPane temp = fxmlLoader.load();
            foodTab.setContent(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void OnVendorTabAction(){
        deleteContent(vendorTab);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/VendorView/mainView.fxml"));
            BorderPane temp = fxmlLoader.load();
            vendorTab.setContent(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void OnMenuTabAction(){
        deleteContent(menuTab);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/MenuView/mainView.fxml"));
            BorderPane temp = fxmlLoader.load();
            menuTab.setContent(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteContent(Tab tab){
        if (tab == foodTab){
            if (vendorTab != null) vendorTab.setContent(null);
            if (menuTab != null) menuTab.setContent(null);
        }
        if (tab == vendorTab){
            foodTab.setContent(null);
            if (menuTab != null) menuTab.setContent(null);
        }
        if (tab == menuTab){
            foodTab.setContent(null);
            if (vendorTab != null) vendorTab.setContent(null);
        }

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}