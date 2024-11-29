package com.ensa;

import com.ensa.classes.ClientDistant;
import com.ensa.interfaces.ChatServerInterface;
import com.ensa.interfaces.ClientDistantInterface;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            // Connexion au serveur
            ChatServerInterface server = (ChatServerInterface) Naming.lookup(ChatServerInterface.LOOKUP_NAME);

            // Création du client
            ClientDistant client = new ClientDistant("3", "TARIQ");
            ClientDistantInterface stub = (ClientDistantInterface) UnicastRemoteObject.exportObject(client, 0);
            server.signIn(stub);

            // Exemple d'envoi de messages
            server.sendToAll(client.getNameDistant(), "Hello everyone!");
            server.sendToOne(client.getNameDistant(), "TARIQ", "Hi TARIQ!");

            // Déconnexion
            //server.signOut(client, "Goodbye!");
            //UnicastRemoteObject.unexportObject(client, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}