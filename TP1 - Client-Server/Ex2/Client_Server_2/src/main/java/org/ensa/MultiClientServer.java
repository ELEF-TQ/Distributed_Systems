package org.ensa;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiClientServer {
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Le serveur est en attente de connexions...");

            while (true) {
                // Accepter les connexions clients
                Socket clientSocket = serverSocket.accept();
                System.out.println("Un client est connecté : " + clientSocket.getInetAddress());

                // Lancer un nouveau thread pour gérer ce client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Message de bienvenue
            out.println("Bienvenue sur le serveur!");

            // Date et heure
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            out.println("La date et l'heure actuelles : " + formatter.format(date));

            String inputLine;
            while (true) {
                out.println("Veuillez entrer une phrase (tapez 'bye' pour quitter) :");
                inputLine = in.readLine();

                if (inputLine.equalsIgnoreCase("bye")) {
                    out.println("Au revoir!");
                    break;
                }

                out.println("En majuscules : " + inputLine.toUpperCase());
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
