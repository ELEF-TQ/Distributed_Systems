package com.ensa;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {
    final static int PORT = 7878;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré, en attente de connexions...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Un client est connecté, IP: " + socket.getInetAddress());

                Connexion clientHandler = new Connexion(socket);
                Thread processus_connexion = new Thread(clientHandler);
                processus_connexion.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connexion implements Runnable {
    private Socket socket;

    public Connexion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) { // Auto-flush set to false

            // Recevoir l'objet Personne
            Personne personne = (Personne) OIS.readObject();
            System.out.println("Personne reçue : " + personne);

            // Calculer l'âge
            Date dateCourante = new Date();
            int age = personne.calcul_age(dateCourante);

            // Envoyer la réponse avec flush explicite
            writer.println("L'âge de " + personne + " est : " + age);
            writer.flush(); // Explicit flush

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}