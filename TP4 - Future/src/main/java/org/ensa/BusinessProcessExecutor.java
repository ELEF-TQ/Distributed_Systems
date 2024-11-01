package org.ensa;

import java.util.concurrent.*;

public class BusinessProcessExecutor {

    public static void main(String[] args) {
        // Durées pour suspendre chaque tâche (en ms)
        int fetchOrderTime = 1000;
        int checkInventoryTime = 800;
        int paymentTime = 600;

        // Création d'un service d'exécution pour les tâches asynchrones
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Étape 1 : Tâche Fetch Order
            OrderFetcher fetcher = new OrderFetcher(fetchOrderTime);
            Future<Integer> fetchOrderResult = executor.submit(fetcher);

            // Récupération de l'OrderID généré par Fetch Order
            Integer orderID = fetchOrderResult.get();
            System.out.println("Order ID fetched: " + orderID);

            // Étape 2 : Tâche Check Inventory
            InventoryChecker checker = new InventoryChecker(orderID, checkInventoryTime);
            Future<Boolean> checkInventoryResult = executor.submit(checker);

            // Récupération de la disponibilité de l'inventaire
            Boolean isInventoryPresent = checkInventoryResult.get();
            System.out.println("Inventory check result: " + isInventoryPresent);

            // Étape 3 : Tâche Accept Payment
            PaymentAcceptor acceptor = new PaymentAcceptor(isInventoryPresent, paymentTime);
            Future<String> paymentResult = executor.submit(acceptor);

            // Récupération du résultat du paiement
            String paymentStatus = paymentResult.get();
            System.out.println("Payment result: " + paymentStatus);

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Erreur dans l'exécution du processus métier : " + e.getMessage());
        } finally {
            // Arrêter le service d'exécution
            executor.shutdown();
        }
    }
}
