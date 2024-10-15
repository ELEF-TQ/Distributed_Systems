package com.ensa;

import java.io.*;
import java.net.*;

public class Client {
    final static String HOST = "localhost";
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // Create a Voiture object
            System.out.print("Entrez la matricule: ");
            String mat = reader.readLine();
            System.out.print("Entrez le carburant: ");
            int carburant = Integer.parseInt(reader.readLine());

            Voiture voiture = new Voiture(mat, carburant);
            // Send the Voiture object to the server
            OOS.writeObject(voiture);
            OOS.flush();

            // Read the server's response
            InputStream IS = socket.getInputStream();
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(IS));
            String response = serverReader.readLine();
            System.out.println("Réponse du serveur: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
