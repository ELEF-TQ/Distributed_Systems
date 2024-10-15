package com.ensa;

import java.io.*;
import java.net.*;

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
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            // Receive the Voiture object
            Voiture voiture = (Voiture) OIS.readObject();
            System.out.println("Voiture reçue : " + voiture);

            // Send a response with fuel level
            writer.println("La voiture " + voiture.getMat() + " a " + voiture.getCarburant() + " unités de carburant.");
            writer.flush();

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
