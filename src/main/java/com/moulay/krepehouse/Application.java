package com.moulay.krepehouse;

import com.moulay.krepehouse.Clients.FoodClient;
import com.moulay.krepehouse.Clients.LoginClient;
import com.moulay.krepehouse.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application extends javafx.application.Application {

    private ServerSocket serverLoginSocket;
    private boolean isLoginRunning = false;
    private ExecutorService clientLoginThreadPool = Executors.newCachedThreadPool();
    private ExecutorService serverLoginThread = Executors.newSingleThreadExecutor();


    private ServerSocket serverFoodSocket;
    private boolean isFoodRunning = false;
    private ExecutorService clientFoodThreadPool = Executors.newCachedThreadPool();
    private ExecutorService serverFoodThread = Executors.newSingleThreadExecutor();

    MainController controller;

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/moulay/krepehouse/loginView.fxml"));
        controller = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load());

//        stage.setTitle("Hello!");
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();

//        startLoginServer();
//        startFoodServer();

    }

    /* start login server */

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

    private void stopLoginServer() {
        if (!isLoginRunning) {
            log("Server is not running");
            return;
        }

        isLoginRunning = false;
        try {
            if (serverLoginSocket != null) {
                serverLoginSocket.close();
            }
            clientLoginThreadPool.shutdownNow();
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
        /*stopLoginServer();
        stopFoodServer();
        serverLoginThread.shutdownNow();
        serverFoodThread.shutdownNow();*/
    }

    /*end login server */

    /* start food server */

    private static final int TIMEOUT = 15000; // 15 seconds
    private static final int BUFFER_SIZE = 2048 * 2048; // 1MB

    private void startFoodServer() {
        if (isFoodRunning) {
            log("Server is already running");
            return;
        }

        serverFoodThread.execute(() -> {
            try {
                serverFoodSocket = new ServerSocket(9091); // Use any available port
                isFoodRunning = true;
                log("Server started on port " + serverFoodSocket.getLocalPort());

                while (isFoodRunning) {
                    try {
                        Socket clientSocket = serverFoodSocket.accept();
                        log("New client connected: " + clientSocket.getInetAddress());


                        // Create new client handler and process in thread pool
                        FoodClient clientHandler = new FoodClient(clientSocket);
                        clientFoodThreadPool.execute(clientHandler);
                    } catch (IOException e) {
                        if (isFoodRunning) {
                            log("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                log("Could not start server: " + e.getMessage());
            } finally {
                isFoodRunning = false;
            }
        });
    }

    private void stopFoodServer() {
        if (!isFoodRunning) {
            log("Server is not running");
            return;
        }

        isFoodRunning = false;
        try {
            if (serverFoodSocket != null) {
                serverFoodSocket.close();
            }
            clientFoodThreadPool.shutdownNow();
            log("Server stopped");
        } catch (IOException e) {
            log("Error stopping server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {


        launch(args);
    }
}