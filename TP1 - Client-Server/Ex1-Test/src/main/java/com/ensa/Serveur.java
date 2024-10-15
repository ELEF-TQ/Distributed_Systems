package com.ensa;

import java.io.*;
import java.net.*;

public class Serveur {
    final static int PORT = 7878;

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("j'attends la connexion d'un client");
        while(true){
            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté, son IP est:" + socket.getInetAddress());
            try {
                InputStream IS = socket.getInputStream();
                InputStreamReader ISR = new InputStreamReader(IS);
                BufferedReader reader = new BufferedReader(ISR);
                String requete = reader.readLine();

                System.out.println("le client a envoyé le message:" + requete);

                OutputStream OS = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(OS);

                writer.println("Bonjour Mr le client");
                writer.flush();

                reader.close();
                writer.close();
                socket.close();
                // serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
