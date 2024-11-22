package com.ensa.application;

import java.rmi.Naming;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GABS {
    public static void main(String[] args) throws Exception{
        Compte cmpt1 = (Compte) Naming.lookup("rmi://localhost:1099/CompteCourant");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.submit(new Operations(cmpt1));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        System.out.println("Finished all threads");
        System.out.println("Le solde est: " + cmpt1.lireSolde());

}
}