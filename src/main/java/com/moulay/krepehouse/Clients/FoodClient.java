package com.moulay.krepehouse.Clients;

import com.moulay.krepehouse.BddPackage.FoodOperation;
import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Models.Vendor;

import java.io.DataOutputStream;
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
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // Create and send response
            ArrayList<SimpleFood> list = operation.getAllFoodByMenuSelected();
//            Vendor vendor = operation.isExistLogin(clientVendor);
            oos.writeObject(list);
            oos.flush();

            // Receive object from client
            /*Object received = ois.readObject();
            if (received instanceof SimpleFood) {
                SimpleFood clientVendor = (SimpleFood) received;
                System.out.println("Received from client: " + clientVendor);

            }*/

        } catch (IOException e) {
            log("Error with client connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void writeNullableString(DataOutputStream dos, String value) throws IOException {
        if (value != null) {
            dos.writeBoolean(true);
            dos.writeUTF(value);
        } else {
            dos.writeBoolean(false);
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
