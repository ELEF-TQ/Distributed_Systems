package com.ensa.application;

public class Operations implements Runnable {

    private final Compte c;

    public Operations(Compte c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            c.debiter(100.0);
            c.crediter(100.0);
            System.out.println("Solde actuel : " + c.lireSolde());
        } catch (Exception e) {
            System.out.println("Erreur lors des opérations : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
