package com.moulay.krepehouse;

import com.moulay.krepehouse.Server.EmbeddedSpringBootApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/FoodView/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        // Handle application close to stop the server
        stage.setOnCloseRequest(e -> {
            EmbeddedSpringBootApp.stopServer();
        });
        stage.show();

        // Start Spring Boot server
        EmbeddedSpringBootApp.startServer();

    }

    public static void main(String[] args) {
        launch();
    }
}