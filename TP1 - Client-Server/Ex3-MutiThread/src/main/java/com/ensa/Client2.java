package com.ensa;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client2 {
    final static String HOST = "localhost";
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // Créer un objet Personne
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.print("Entrez le nom: ");
            String nom = reader.readLine();
            System.out.print("Entrez le prénom: ");
            String prenom = reader.readLine();
            System.out.print("Entrez la date de naissance (dd/MM/yyyy): ");
            String dateNaissanceStr = reader.readLine();
            Date dateNaissance = sdf.parse(dateNaissanceStr);

            Personne personne = new Personne(nom, prenom, dateNaissance);
            // Envoyer l'objet Personne au serveur
            OOS.writeObject(personne);
            OOS.flush();

            // Lire la réponse du serveur
            InputStream IS = socket.getInputStream();
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(IS));
            String response = serverReader.readLine();
            System.out.println("Réponse du serveur: " + response);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}