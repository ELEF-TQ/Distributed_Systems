package org.ensa;

import java.util.Random;
import java.util.concurrent.Callable;

public class OrderFetcher implements Callable<Integer> {

    // Attribut statique pour générer un ID de commande aléatoire
    private static final Random OrderID = new Random();
    private final int t; // Durée pour suspendre le thread

    // Constructeur
    public OrderFetcher(int t) {
        this.t = t;
    }

    // Méthode FetcherCall qui simule un appel I/O en suspendant le thread pendant 't' millisecondes
    private void FetcherCall(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // Gestion de l'exception si le thread est interrompu pendant le sommeil
            Thread.currentThread().interrupt(); // Restaurer le statut d'interruption
            System.out.println("Le thread a été interrompu : " + e.getMessage());
        }
    }

    // Redéfinition de la méthode call() de l'interface Callable
    @Override
    public Integer call() {
        // Simuler l'appel I/O
        FetcherCall(t);

        // Générer et retourner un OrderID aléatoire entre 0 et 10
        return OrderID.nextInt(11); // nextInt(11) génère un nombre entre 0 et 10 inclus
    }
}
