package org.ensa;

import java.util.concurrent.Callable;

public class InventoryChecker implements Callable<Boolean> {

    // Attribut représentant l'ID de commande récupéré depuis la tâche précédente
    private Integer OrderID;
    private int t; // Durée pour suspendre le thread

    // Constructeur
    public InventoryChecker(Integer OrderID, int t) {
        this.OrderID = OrderID;
        this.t = t;
    }

    // Méthode CheckerCall qui simule un appel I/O en suspendant le thread pendant 't' millisecondes
    private void CheckerCall(int t) {
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
    public Boolean call() {
        // Simuler l'appel I/O
        CheckerCall(t);
        // Retourner true si OrderID est divisible par 2, false sinon
        return OrderID != null && OrderID % 2 == 0;
    }
}
