package com.moulay.krepehouse;

import com.moulay.krepehouse.Clients.BillPrintClient;
import com.moulay.krepehouse.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application extends javafx.application.Application {

    private ServerSocket serverPrintSocket;
    private boolean isPrintRunning = false;
    private ExecutorService clientPrintThreadPool = Executors.newCachedThreadPool();
    private ExecutorService serverPrintThread = Executors.newSingleThreadExecutor();

    MainController controller;

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/loginView.fxml"));
        controller = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("One Crepe");
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/com/moulay/krepehouse/Images/one_crepe_light_bg.png"))));
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();

        startPrintServer();

    }

    /* start login server */

    private void startPrintServer() {
        if (isPrintRunning) {
            log("Server is already running");
            return;
        }

        serverPrintThread.execute(() -> {
            try {
                serverPrintSocket = new ServerSocket(9090); // Use any available port
                isPrintRunning = true;
                log("Server started on port " + serverPrintSocket.getLocalPort());

                while (isPrintRunning) {
                    try {
                        Socket clientSocket = serverPrintSocket.accept();
                        log("New client connected: " + clientSocket.getInetAddress());

                        // Create new client handler and process in thread pool
                        BillPrintClient clientHandler = new BillPrintClient(clientSocket);
                        clientPrintThreadPool.execute(clientHandler);
                    } catch (IOException e) {
                        if (isPrintRunning) {
                            log("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                log("Could not start server: " + e.getMessage());
            } finally {
                isPrintRunning = false;
            }
        });
    }

    private void stopPrintServer() {
        if (!isPrintRunning) {
            log("Server is not running");
            return;
        }

        isPrintRunning = false;
        try {
            if (serverPrintSocket != null) {
                serverPrintSocket.close();
            }
            clientPrintThreadPool.shutdownNow();
            log("Server stopped");
        } catch (IOException e) {
            log("Error stopping server: " + e.getMessage());
        }
    }

    // Make this package-private if ClientHandler is in the same package
    public void log(String message) {
        System.out.println("log server : " + message);
    }

    @Override
    public void stop() {
        stopPrintServer();
        serverPrintThread.shutdownNow();
    }

    /*end login server */

    public static void main(String[] args) {launch(args);}
}