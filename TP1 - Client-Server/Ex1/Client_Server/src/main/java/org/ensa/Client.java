package org.ensa;

import java.io.*;
import java.net.Socket;

public class Client {

    final static int PORT = 7878;
    final static String HOST = "localhost";

    public static void main(String args[]) throws Exception {
        try {
            Socket socket = new Socket(HOST, PORT);

            System.out.println("j'ai envoyé la requête de connexion via le port N°'" + socket.getLocalPort());
            OutputStream OS = socket.getOutputStream();

            PrintWriter writer = new PrintWriter(OS);
            String requete = "Bonjour Mr le serveur";

            writer.println(requete);
            writer.flush();

            InputStream IS = socket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);

            BufferedReader reader = new BufferedReader(ISR);
            String reponse = reader.readLine();

            System.out.println("Le serveur m'a répondu par le message:" + reponse);

            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}