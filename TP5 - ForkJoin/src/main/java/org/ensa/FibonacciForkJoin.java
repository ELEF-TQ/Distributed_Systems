package org.ensa;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int nombre;
    private final int seuil;

    public FibonacciForkJoin(int seuil, int nombre) {
        this.nombre = nombre;
        this.seuil = seuil;
    }

    @Override
    protected Integer compute() {
        // Si la valeur est sous le seuil, calculer directement
        if (nombre <= seuil) {
            return fibonacciDirect(nombre);
        } else {
            // Sinon, diviser la tâche en sous-tâches
            FibonacciForkJoin leftTask = new FibonacciForkJoin(seuil, nombre - 1);
            FibonacciForkJoin rightTask = new FibonacciForkJoin(seuil, nombre - 2);

            // Fork le leftTask pour qu'il soit exécuté en parallèle
            leftTask.fork();
            // Calculer le rightTask dans le thread actuel
            int rightResult = rightTask.compute();
            // Joindre le résultat de leftTask
            int leftResult = leftTask.join();

            // Retourner la somme des résultats
            return leftResult + rightResult;
        }
    }

    // Calcul direct pour les petites valeurs de Fibonacci
    private int fibonacciDirect(int n) {
        if (n <= 1) return n;
        return fibonacciDirect(n - 1) + fibonacciDirect(n - 2);
    }

    public static void main(String[] args) {
        int nombre = 45; // Valeur de Fibonacci à calculer
        int seuil = 8;   // Seuil de division
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        long startTime = System.currentTimeMillis();

        FibonacciForkJoin fibonacciTask = new FibonacciForkJoin(seuil, nombre);
        int resultat = forkJoinPool.invoke(fibonacciTask);

        long endTime = System.currentTimeMillis();

        System.out.println("Résultat final : " + resultat);
        System.out.println("Temps d'exécution : " + (endTime - startTime) + " ms");
    }
}
