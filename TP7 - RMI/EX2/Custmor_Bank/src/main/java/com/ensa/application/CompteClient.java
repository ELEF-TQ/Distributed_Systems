package com.ensa.application;

import java.rmi.Naming;


public class CompteClient   {

    public static void main(String[] args) {
        try {
            // Accéder à l'objet distant
            Compte stubCompte = (Compte) Naming.lookup("rmi://localhost:1099/CompteCourant");

            // Effectuer des opérations sur le compte
            System.out.println("Solde initial : " + stubCompte.lireSolde());

            stubCompte.debiter(500);
            System.out.println("Après débit de 500 DH : " + stubCompte.lireSolde());

            stubCompte.crediter(800);
            System.out.println("Après crédit de 800 DH : " + stubCompte.lireSolde());

        } catch (Exception e) {
            System.out.println("Erreur dans le client : " + e.getMessage());
            e.printStackTrace();
        }
    }
}