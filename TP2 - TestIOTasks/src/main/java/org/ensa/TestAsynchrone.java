package org.ensa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TestAsynchrone {

    public static Integer random() {
        return new Random().nextInt(10);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long begin, end, time = 0, sum = 0;
        int numTasks = 1000; // Nombre de tâches à exécuter

        // Création d'un pool de threads avec un nombre fixe de threads (par exemple 4)
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // Liste pour stocker les futures (résultats des tâches)
        List<Future<Integer>> futures = new ArrayList<>();

        begin = System.currentTimeMillis();

        // Lancer les tâches asynchrones
        for (int j = 0; j < numTasks; j++) {
            // Soumettre une tâche au pool de threads
            futures.add(executor.submit(() -> {
                try {
                    Thread.sleep(5);  // Simulation de délai
                    return random();  // Retourne un nombre aléatoire
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        // Attendre que toutes les tâches soient terminées et récupérer les résultats
        for (Future<Integer> future : futures) {
            sum += future.get();  // Récupère le résultat de chaque tâche
        }

        // Fermer le pool d'exécuteurs
        executor.shutdown();

        end = System.currentTimeMillis();
        time = end - begin;

        System.out.println("Sum of random numbers: " + sum);
        System.out.println("Elapsed Time: " + time + " Milliseconds");
    }
}
