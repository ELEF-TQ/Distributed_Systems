package org.ensa;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureWithVirtualThreads {

    public static void main(String[] args) {
        int fetchOrderTime = 10;
        int checkInventoryTime = 8;
        int paymentTime = 6;

        long totalExecutionTime = 0;

        // Création d'un pool de threads virtuels
        try (ExecutorService virtualThreadPool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) { // 5 essais
                long startTime = System.currentTimeMillis();

                // Tableau de CompletableFuture pour stocker chaque tâche
                CompletableFuture<?>[] futures = new CompletableFuture[1000];

                for (int j = 0; j < 1000; j++) {
                    futures[j] = CompletableFuture.supplyAsync(() -> {
                                simulateDelay(fetchOrderTime);
                                int orderId = new Random().nextInt(11);
                                return orderId;
                            }, virtualThreadPool)
                            .thenApplyAsync(orderId -> {
                                simulateDelay(checkInventoryTime);
                                boolean isInventoryAvailable = orderId % 2 == 0;
                                return isInventoryAvailable;
                            }, virtualThreadPool)
                            .thenAcceptAsync(isInventoryAvailable -> {
                                simulateDelay(paymentTime);
                            }, virtualThreadPool);
                }

                // Attendre que toutes les tâches soient terminées
                CompletableFuture.allOf(futures).join();

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalExecutionTime += executionTime;

                System.out.println("Essai " + (i + 1) + ": " + executionTime + " ms");
            }

            System.out.println("Temps moyen d'exécution avec threads virtuels : " + (totalExecutionTime / 5) + " ms");
        }
    }

    // Méthode pour simuler un délai
    private static void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
