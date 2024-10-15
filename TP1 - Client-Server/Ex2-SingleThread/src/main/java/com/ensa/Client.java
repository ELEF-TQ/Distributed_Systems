package com.ensa;

import java.io.*;
import java.net.Socket;

public class Client {
    final static int PORT = 7878;
    final static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            // Création de la socket pour se connecter au serveur
            Socket socket = new Socket(HOST, PORT);

            // Flux d'entrée pour recevoir les messages du serveur
            InputStream IS = socket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader reader = new BufferedReader(ISR);

            // Flux de sortie pour envoyer des messages au serveur
            OutputStream OS = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(OS);  // Auto-flush désactivé

            // Flux pour lire l'entrée de l'utilisateur (console)
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String serverResponse;
            String userMessage;

            // Boucle de communication avec le serveur
            while (true) {
                // Lire la réponse du serveur
                serverResponse = reader.readLine();
                if (serverResponse == null) {
                    break;
                }

                // Afficher la réponse du serveur
                System.out.println("Serveur: " + serverResponse);

                // Si le serveur envoie "BYE", aprés que l'utilisateur a déjà envoyer "bye" terminer la communication
                if (serverResponse.equals("BYE")) {
                    break;
                }

                // Si le serveur demande une entrée utilisateur
                if (serverResponse.contains("Veuillez entrer une phrase")) {
                    // Lire l'entrée de l'utilisateur
                    userMessage = userInput.readLine();

                    // Envoyer l'entrée au serveur
                    writer.println(userMessage);
                    writer.flush(); // Appel explicite à flush pour forcer l'envoi des données
                }
            }

            // Libérer les ressources dans le bloc try
            reader.close();
            writer.close();
            userInput.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
