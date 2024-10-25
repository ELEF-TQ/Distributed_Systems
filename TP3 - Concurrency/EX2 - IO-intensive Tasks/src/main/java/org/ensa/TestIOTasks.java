package org.ensa;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class TestIOTasks {
    public static void main(String[] args) throws InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors(); // Corrected variable name convention


        long begin, end, time = 0;

        // Create a fixed-size thread pool based on available processors
        ExecutorService executor = Executors.newFixedThreadPool(10000);

        begin = System.currentTimeMillis(); // Start timing

        // Submit 100,000 IO tasks
        for (int j = 0; j < 100000; j++) {
            executor.submit(new IOTask());
        }

        executor.shutdown(); // Stop accepting new tasks

        // Wait until all tasks are completed
        while (!executor.isTerminated()) {
            Thread.sleep(1);
        }

        System.out.println("Finished all threads");

        // Calculate and print the elapsed time
        end = System.currentTimeMillis();
        time = end - begin;
        System.out.println("Elapsed Time: " + time + " Milliseconds");

        // Display the number of cores
        System.out.println("Number of Cores: " + poolSize);
    }

    // Define the IOTask class
    static class IOTask implements Runnable {
        @Override
        public void run() {
            IOcall();
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        }

        // Simulates an IO operation with a 5 ms sleep
        private void IOcall() {
            try {
                Thread.sleep(5); // Simulate IO operation delay
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new Random().nextInt(); // Perform a random number generation as a placeholder
        }
    }
}
