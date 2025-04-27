package com.moulay.krepehouse;

import com.moulay.krepehouse.Clients.LoginClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HelloApplication extends Application {

    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private ExecutorService clientThreadPool = Executors.newCachedThreadPool();
    private ExecutorService serverLoginThread = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/moulay/krepehouse/BillView/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        startLoginServer();

    }

    private void startLoginServer() {
        if (isRunning) {
            log("Server is already running");
            return;
        }

        serverLoginThread.execute(() -> {
            try {
                serverSocket = new ServerSocket(9090); // Use any available port
                isRunning = true;
                log("Server started on port " + serverSocket.getLocalPort());

                while (isRunning) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        log("New client connected: " + clientSocket.getInetAddress());

                        // Create new client handler and process in thread pool
                        LoginClient clientHandler = new LoginClient(clientSocket);
                        clientThreadPool.execute(clientHandler);
                    } catch (IOException e) {
                        if (isRunning) {
                            log("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                log("Could not start server: " + e.getMessage());
            } finally {
                isRunning = false;
            }
        });
    }

    private void stopServer() {
        if (!isRunning) {
            log("Server is not running");
            return;
        }

        isRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            clientThreadPool.shutdownNow();
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
        stopServer();
        serverLoginThread.shutdownNow();
    }
    public static void main(String[] args) {
        launch();
    }
}