package org.ensa;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureWithLambda {

    public static void main(String[] args) {
        int fetchOrderTime = 300;
        int checkInventoryTime = 150;
        int paymentTime = 200;

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Step 1: Fetch Order Task
        CompletableFuture<Integer> fetchOrderFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(fetchOrderTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Fetch Order task interrupted: " + e.getMessage());
            }
            int orderId = new Random().nextInt(11);
            System.out.println("Order ID fetched: " + orderId);
            return orderId;
        }, executor);

        // Step 2: Check Inventory Task, chained to Fetch Order
        CompletableFuture<Boolean> checkInventoryFuture = fetchOrderFuture.thenApplyAsync(orderId -> {
            try {
                Thread.sleep(checkInventoryTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Check Inventory task interrupted: " + e.getMessage());
            }
            boolean isInventoryPresent = orderId % 2 == 0;
            System.out.println("Inventory check result: " + isInventoryPresent);
            return isInventoryPresent;
        }, executor);

        // Step 3: Accept Payment Task, chained to Check Inventory
        CompletableFuture<String> paymentFuture = checkInventoryFuture.thenApplyAsync(isInventoryPresent -> {
            try {
                Thread.sleep(paymentTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Accept Payment task interrupted: " + e.getMessage());
            }
            String paymentStatus = isInventoryPresent ? "Payment Accepted" : "Payment not Accepted";
            System.out.println("Payment result: " + paymentStatus);
            return paymentStatus;
        }, executor);

        // Handle final result
        paymentFuture.thenAccept(paymentStatus ->
                System.out.println("Final Payment Status: " + paymentStatus)
        ).exceptionally(e -> {
            System.out.println("Error in business process execution: " + e.getMessage());
            return null;
        }).thenRun(executor::shutdown);
    }
}
