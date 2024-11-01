package org.ensa;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureWithLambda {

    public static void main(String[] args) {
        int fetchOrderTime = 10; // Reduced time for faster debugging
        int checkInventoryTime = 8;
        int paymentTime = 6;

        long totalExecutionTime = 0;

        for (int i = 0; i < 5; i++) { // Run 5 tests
            ExecutorService executor = Executors.newFixedThreadPool(20); // Increased pool size for better concurrency
            long startTime = System.currentTimeMillis();

            CompletableFuture<?>[] futures = new CompletableFuture[1000];
            for (int j = 0; j < 1000; j++) {
                CompletableFuture<Integer> fetchOrderFuture = CompletableFuture.supplyAsync(() -> {
                    try { Thread.sleep(fetchOrderTime); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    return new Random().nextInt(11);
                }, executor);

                CompletableFuture<Boolean> checkInventoryFuture = fetchOrderFuture.thenApplyAsync(orderId -> {
                    try { Thread.sleep(checkInventoryTime); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    return orderId % 2 == 0;
                }, executor);

                CompletableFuture<String> paymentFuture = checkInventoryFuture.thenApplyAsync(isInventoryPresent -> {
                    try { Thread.sleep(paymentTime); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    return isInventoryPresent ? "Payment Accepted" : "Payment not Accepted";
                }, executor);

                futures[j] = paymentFuture; // Store to ensure all tasks are complete
            }

            CompletableFuture.allOf(futures).join(); // Wait for all 1000 tasks to complete

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            totalExecutionTime += executionTime;

            System.out.println("CompletableFuture Test Execution " + (i + 1) + ": " + executionTime + " ms");
            executor.shutdown();
        }

        System.out.println("Average CompletableFuture Execution Time: " + (totalExecutionTime / 5) + " ms");
    }
}
