package com.ensa.application;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class CompteServeur {
    public static void main(String[] args) {
        try {
            // Lancer le registre RMI sur le port 1099
            LocateRegistry.createRegistry(1099);

            // Créer une instance de l'objet distant
            Compte compte = new CompteImpl();

            // Enregistrer l'objet distant avec l'URL
            Naming.rebind("rmi://localhost:1099/CompteCourant", (Remote) compte);

            System.out.println("Serveur RMI démarré et objet Compte enregistré.");
        } catch (Exception e) {
            System.out.println("Erreur dans le serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
