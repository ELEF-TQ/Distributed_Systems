package org.ensa;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FibonacciExecutorService {
    private int nombre;
    private ExecutorService executorService;

    public FibonacciExecutorService(int nombre) {
        this.nombre = nombre;
        // Créer un pool de threads avec un nombre fixe de threads
        this.executorService = Executors.newFixedThreadPool(12);  // Par exemple, 4 threads
    }

    // Tâche Callable qui sera soumise à l'ExecutorService
    private class FibonacciTask implements Callable<Integer> {
        private int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        public Integer call() throws Exception {
            return calculElementaire(n);
        }

        // Calcul Fibonacci de manière récursive
        private int calculElementaire(int n) {
            if (n <= 1) {
                return n;
            } else {
                return calculElementaire(n - 1) + calculElementaire(n - 2);
            }
        }
    }

    // Méthode pour exécuter les calculs Fibonacci en parallèle
    public void compute() {
        try {
            // Soumettre la tâche au pool de threads
            FibonacciTask task = new FibonacciTask(nombre);
            Future<Integer> resultFuture = executorService.submit(task);

            // Attendre le résultat du calcul et l'afficher
            int resultat = resultFuture.get();
            System.out.println("Résultat final : " + resultat);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Fermer l'executor après le travail
        }
    }

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        FibonacciExecutorService f = new FibonacciExecutorService(45);  // Calcul de Fibonacci pour n = 45
        f.compute();
        long fin = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (fin - depart) + " ms");
    }
}