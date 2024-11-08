package org.ensa;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int nombre;

    public FibonacciForkJoin(int n) {
        this.nombre = n;
    }

    @Override
    protected Integer compute() {
        if (nombre <= 1) {
            return nombre;
        }
        FibonacciForkJoin f1 = new FibonacciForkJoin(nombre - 1);
        f1.fork(); // Lance f1 en sous-tâche
        FibonacciForkJoin f2 = new FibonacciForkJoin(nombre - 2);
        return f2.compute() + f1.join(); // Calcule f2, puis combine avec f1
    }

    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long depart = System.currentTimeMillis();
        FibonacciForkJoin task = new FibonacciForkJoin(45);
        int resultat = pool.invoke(task);
        System.out.println("Résultat final : " + resultat);
        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
