package com.ensa;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Classe principale du serveur
public class ServerMT {
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré, en attente de connexions...");

            while (true) {
                // Accepter une connexion d'un client
                Socket socket = serverSocket.accept();
                System.out.println("Un client est connecté, IP: " + socket.getInetAddress());

                // Créer un nouveau thread pour gérer la connexion du client
                Connexion clientHandler = new Connexion(socket);
                Thread processus_connexion = new Thread(clientHandler);
                processus_connexion.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Classe pour gérer la connexion avec un client
class Connexion implements Runnable {
    private Socket socket;

    public Connexion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            // Message de bienvenue
            writer.println("Bienvenue sur le serveur!");

            // Affichage de la date et de l'heure actuelles
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            writer.println("La date et l'heure actuelles: " + formatter.format(date));

            String requete;
            while (true) {
                writer.println("Veuillez entrer une phrase : ");
                requete = reader.readLine();

                if (requete == null) {
                    break;
                }

                // Réponse en majuscules
                String reponse = requete.toUpperCase();
                writer.println("En majuscules : " + reponse);

                // Fin de la communication
                if (requete.equalsIgnoreCase("bye")) {
                    writer.println("Au revoir, merci d'avoir utilisé le serveur.");
                    break;
                }
            }

            // Fermeture des flux et de la socket
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

