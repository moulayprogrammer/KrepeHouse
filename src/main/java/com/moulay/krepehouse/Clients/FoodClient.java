package com.moulay.krepehouse.Clients;

import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Models.Vendor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class FoodClient implements Runnable{

    private final Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;


    private final FoodOperation operation = new FoodOperation();


    public FoodClient(Socket socket) throws IOException {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {
            // Initialize streams
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());

            // Receive request from client
            String request = (String) ois.readObject();
            System.out.println("Received request: " + request);


            if (request.equals("get")){

                // Create sample food list
                List<SimpleFood> foodList = operation.getAllFoodByMenuSelected();

                // Create and send response
                oos.writeObject(foodList);
                oos.flush();

                // Wait for client acknowledgment if needed
                try {
//                    clientSocket.setSoTimeout(5000); // 5-second timeout
                    Object ack = ois.readObject(); // Wait for client acknowledgment
                } catch (SocketTimeoutException e) {
                    System.out.println("Client didn't acknowledge, but data was sent");
                }
            }



        } catch (IOException | ClassNotFoundException e) {
            log("Error with client connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private String processInput(String input) {
        // Add your business logic here
        // This is a simple echo server with added processing
        return "Server response: " + input.toUpperCase();
    }

    private void closeResources() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
           log("Error closing client resources: " + e.getMessage());
        }
    }

    // Make this package-private if ClientHandler is in the same package
    public void log(String message) {
        System.out.println("log server : " + message);
    }
}
