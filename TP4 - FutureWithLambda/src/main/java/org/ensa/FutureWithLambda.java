package org.ensa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FutureWithLambda {

    public static void main(String[] args) {
        int fetchOrderTime = 10; // Reduced time for faster debugging
        int checkInventoryTime = 8;
        int paymentTime = 6;

        long totalExecutionTime = 0;

        for (int i = 0; i < 5; i++) { // Run 5 tests
            ExecutorService executor = Executors.newFixedThreadPool(20); // Increased pool size for better concurrency
            long startTime = System.currentTimeMillis();
            List<Future<String>> futures = new ArrayList<>();

            for (int j = 0; j < 1000; j++) {
                Future<Integer> fetchOrderFuture = executor.submit(() -> {
                    Thread.sleep(fetchOrderTime);
                    return new Random().nextInt(11);
                });

                Future<Boolean> checkInventoryFuture = executor.submit(() -> {
                    int orderId = fetchOrderFuture.get(500, TimeUnit.MILLISECONDS); // Timeout for get
                    Thread.sleep(checkInventoryTime);
                    return orderId % 2 == 0;
                });

                Future<String> paymentFuture = executor.submit(() -> {
                    boolean isInventoryPresent = checkInventoryFuture.get(500, TimeUnit.MILLISECONDS); // Timeout for get
                    Thread.sleep(paymentTime);
                    return isInventoryPresent ? "Payment Accepted" : "Payment not Accepted";
                });

                futures.add(paymentFuture);
            }

            // Ensure all tasks complete
            for (Future<String> future : futures) {
                try {
                    future.get(1, TimeUnit.SECONDS); // Timeout for each final result
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            totalExecutionTime += executionTime;

            System.out.println("Future Test Execution " + (i + 1) + ": " + executionTime + " ms");
            executor.shutdown();
        }

        System.out.println("Average Future Execution Time: " + (totalExecutionTime / 5) + " ms");
    }
}
