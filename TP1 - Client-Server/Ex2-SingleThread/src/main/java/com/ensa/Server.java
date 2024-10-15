package com.ensa;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    final static int PORT = 7878;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("En attente de connexion d'un client...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté, IP: " + socket.getInetAddress());

            try {

                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream);

                writer.println("Bienvenue sur le serveur!");
                writer.flush();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                writer.println("La date et l'heure actuelles: " + formatter.format(date));
                writer.flush();

                String requete;
                while (true) {
                    writer.println("Veuillez entrer une phrase : ");
                    writer.flush();
                    requete = reader.readLine();

                    if (requete == null) {
                        break;
                    }

                    String reponse = requete.toUpperCase();
                    writer.println("En majuscules : " + reponse);
                    writer.flush();


                    if (requete.equalsIgnoreCase("bye")) {
                        writer.println("Au revoir, merci d'avoir utiliser le serveur.");
                        writer.flush();
                        break;
                    }
                }


                reader.close();
                writer.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
