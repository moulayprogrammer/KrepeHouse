package com.moulay.krepehouse.Clients;

import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Vendor;

import java.io.*;
import java.net.Socket;

public class LoginClient implements Runnable{

    private final Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    private final VendorOperation operation = new VendorOperation();


    public LoginClient(Socket socket) throws IOException {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {
            // Initialize streams
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // Receive object from client
            Object received = ois.readObject();
            if (received instanceof Vendor) {
                Vendor clientVendor = (Vendor) received;
                System.out.println("Received from client: " + clientVendor);

                // Create and send response
                Vendor vendor = operation.isExistLogin(clientVendor);
                oos.writeObject(vendor);
                oos.flush();
            }

//            closeResources();

        } catch (IOException | ClassNotFoundException e) {
            log("Error with client connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
//            closeResources();
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
