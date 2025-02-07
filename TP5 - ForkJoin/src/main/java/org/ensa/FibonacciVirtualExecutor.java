//package org.ensa;
//
//import java.util.concurrent.*;
//
//public class FibonacciVirtualExecutor {
//    private final int nombre;
//
//    public FibonacciVirtualExecutor(int n) {
//        this.nombre = n;
//    }
//
//    private int calculElementaire(int n, ExecutorService executor) throws ExecutionException, InterruptedException {
//        if (n <= 1) {
//            return n;
//        }
//        Future<Integer> f1 = executor.submit(() -> calculElementaire(n - 1, executor));
//        int f2 = calculElementaire(n - 2, executor);
//        return f1.get() + f2;
//    }
//
//    public int compute() throws ExecutionException, InterruptedException {
//        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
//        try {
//            return calculElementaire(nombre, executor);
//        } finally {
//            executor.shutdown();
//        }
//    }
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        long depart = System.currentTimeMillis();
//        FibonacciVirtualExecutor fibonacci = new FibonacciVirtualExecutor(45);
//        int resultat = fibonacci.compute();
//        System.out.println("Résultat final : " + resultat);
//        long fin = System.currentTimeMillis();
//        System.out.println("Time : " + (fin - depart) + " ms");
//    }
//}
