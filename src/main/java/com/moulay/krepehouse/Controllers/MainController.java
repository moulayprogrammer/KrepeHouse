package com.moulay.krepehouse.Controllers;

import com.moulay.krepehouse.Application;

import com.moulay.krepehouse.Clients.LoginClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {


    @FXML
    Tab foodTab, vendorTab,menuTab;

    private ServerSocket serverLoginSocket;
    private boolean isLoginRunning = false;
    private ExecutorService clientLoginThreadPool = Executors.newCachedThreadPool();
    private ExecutorService serverLoginThread = Executors.newSingleThreadExecutor();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vendorTab.setContent(new TextField());

        System.out.println("server is start");
//        statusLabel.setText("Spring Boot: RUNNING");


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/FoodView/mainView.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/FoodView/mainView.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/VendorView/mainView.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/MenuView/mainView.fxml"));
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

    private void startLoginServer() {
        if (isLoginRunning) {
            log("Server is already running");
            return;
        }

        serverLoginThread.execute(() -> {
            try {
                serverLoginSocket = new ServerSocket(9090); // Use any available port
                isLoginRunning = true;
                log("Server started on port " + serverLoginSocket.getLocalPort());

                while (isLoginRunning) {
                    try {
                        Socket clientSocket = serverLoginSocket.accept();
                        log("New client connected: " + clientSocket.getInetAddress());

                        // Create new client handler and process in thread pool
                        LoginClient clientHandler = new LoginClient(clientSocket);
                        clientLoginThreadPool.execute(clientHandler);
                    } catch (IOException e) {
                        if (isLoginRunning) {
                            log("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                log("Could not start server: " + e.getMessage());
            } finally {
                isLoginRunning = false;
            }
        });
    }

    public void log(String message) {
        System.out.println("log server : " + message);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}