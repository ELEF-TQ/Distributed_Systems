package org.ensa;

import java.io.*;
import java.net.*;

public class Server {
    final static int PORT = 7878;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Le serveur est en attente de connexions...");

            while (isRunning) {
                try {
                    // Accept the client connection
                    Socket socket = serverSocket.accept();
                    System.out.println("Un client est connecté, son IP est : " + socket.getInetAddress());

                    // Receive the message from the client
                    InputStream IS = socket.getInputStream();
                    InputStreamReader ISR = new InputStreamReader(IS);
                    BufferedReader reader = new BufferedReader(ISR);
                    String requete = reader.readLine();
                    System.out.println("Le client a envoyé le message : " + requete);

                    // Send a response to the client
                    OutputStream OS = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(OS);
                    writer.println("Bonjour, Mr le client");
                    writer.flush();

                    // Close the connection with the client
                    reader.close();
                    writer.close();
                    socket.close();

                } catch (Exception e) {
                    System.err.println("Erreur lors de la gestion du client : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to stop the server
    public static void stopServer() {
        isRunning = false;
        System.out.println("Le serveur va s'arrêter...");
    }
}
