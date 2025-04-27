package com.moulay.krepehouse.Clients;

import java.io.*;
import java.net.Socket;

public class LoginClient implements Runnable{

    private final Socket clientSocket;

    private BufferedReader in;
    private PrintWriter out;

    public LoginClient(Socket socket) throws IOException {
        this.clientSocket = socket;
    }




    @Override
    public void run() {
        try {
            // Initialize streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            log("Client connected from: " + clientSocket.getInetAddress());

            // Send welcome message
            out.println("Welcome to the server! Type 'exit' to quit.");

            // Process client messages
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(inputLine)) {
                    break;
                }

                log("Received from client: " + inputLine);

                // Process input and send response
                String response = processInput(inputLine);
                out.println(response);
            }

            log("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            log("Error with client connection: " + e.getMessage());
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
            if (in != null) in.close();
            if (out != null) out.close();
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
