package com.moulay.krepehouse.Clients;

import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Vendor;

import java.io.*;
import java.net.Socket;

public class BillPrintClient implements Runnable{

    private final Socket billPrintSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;


    public BillPrintClient(Socket socket) throws IOException {
        this.billPrintSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Initialize streams
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(billPrintSocket.getInputStream())
            );

            String msg = in.readLine();
            System.out.println("Received from client: " + msg);

            closeResources();

        } catch (IOException e) {
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
            if (billPrintSocket != null) billPrintSocket.close();
        } catch (IOException e) {
           log("Error closing client resources: " + e.getMessage());
        }
    }

    // Make this package-private if ClientHandler is in the same package
    public void log(String message) {
        System.out.println("log server : " + message);
    }
}
