package org.ensa;

import java.io.*;
import java.net.Socket;

public class Client {
    final static int PORT = 7878;
    final static String HOST = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Serveur: " + serverResponse);
                if (serverResponse.contains("Veuillez entrer une phrase")) {
                    // Envoyer une phrase à partir de l'entrée utilisateur
                    String userMessage = userInput.readLine();
                    out.println(userMessage);
                    if (userMessage.equalsIgnoreCase("bye")) {
                        break;
                    }
                }
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
