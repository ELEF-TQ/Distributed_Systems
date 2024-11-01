package org.ensa;

import java.util.Random;
import java.util.concurrent.*;

public class FutureWithLambda {

    public static void main(String[] args) {
        int fetchOrderTime = 300;
        int checkInventoryTime = 150;
        int paymentTime = 200;

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Step 1: Fetch Order Task
            Future<Integer> fetchOrderFuture = executor.submit(() -> {
                // Simulate I/O delay
                try {
                    Thread.sleep(fetchOrderTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Fetch Order task interrupted: " + e.getMessage());
                }
                // Generate random OrderID
                int orderId = new Random().nextInt(11);
                System.out.println("Order ID fetched: " + orderId);
                return orderId;
            });

            // Retrieve OrderID
            Integer orderId = fetchOrderFuture.get();
            System.out.println("Fetched Order ID: " + orderId);

            // Step 2: Check Inventory Task
            Future<Boolean> checkInventoryFuture = executor.submit(() -> {
                // Simulate I/O delay
                try {
                    Thread.sleep(checkInventoryTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Check Inventory task interrupted: " + e.getMessage());
                }
                // Check if OrderID is even for inventory availability
                boolean isInventoryPresent = orderId % 2 == 0;
                System.out.println("Inventory check result: " + isInventoryPresent);
                return isInventoryPresent;
            });

            // Retrieve Inventory Check Result
            Boolean isInventoryPresent = checkInventoryFuture.get();
            System.out.println("Inventory Check Result: " + isInventoryPresent);

            // Step 3: Accept Payment Task
            Future<String> paymentFuture = executor.submit(() -> {
                // Simulate I/O delay
                try {
                    Thread.sleep(paymentTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Accept Payment task interrupted: " + e.getMessage());
                }
                // Determine payment status based on inventory presence
                String paymentStatus = isInventoryPresent ? "Payment Accepted" : "Payment not Accepted";
                System.out.println("Payment result: " + paymentStatus);
                return paymentStatus;
            });

            // Retrieve Payment Result
            String paymentStatus = paymentFuture.get();
            System.out.println("Final Payment Status: " + paymentStatus);

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error in business process execution: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
