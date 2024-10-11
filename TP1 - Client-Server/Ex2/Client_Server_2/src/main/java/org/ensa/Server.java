package org.ensa;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Le serveur est en attente de connexions...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Un client est connecté, son IP est : " + clientSocket.getInetAddress());

                // Création des flux de communication
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Afficher un message de bienvenue
                out.println("Bienvenue sur le serveur!");

                // Afficher la date et l'heure actuelles
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                out.println("La date et l'heure actuelles : " + formatter.format(date));

                String inputLine;
                while (true) {
                    // Demander une phrase
                    out.println("Veuillez entrer une phrase (tapez 'bye' pour quitter) :");
                    inputLine = in.readLine();

                    // Vérifier si le client a saisi "bye"
                    if (inputLine.equalsIgnoreCase("bye")) {
                        out.println("Au revoir!");
                        break;
                    }

                    // Convertir la phrase en majuscule et l'envoyer au client
                    out.println("En majuscules : " + inputLine.toUpperCase());
                }

                // Fermer la connexion client
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
