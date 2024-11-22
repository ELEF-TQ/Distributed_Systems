package com.ensa.application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CompteImpl extends UnicastRemoteObject implements Compte {
    private  double solde ;

    public CompteImpl() throws RemoteException {
        super();
        this.solde = 1000;
    }
    @Override
    public synchronized void debiter(double montant) throws RemoteException {
        solde -= montant;
        System.out.println("Montant débité : " + montant + ", Nouveau solde : " + solde);
    }


    @Override
    public synchronized void crediter(double montant) throws RemoteException {
        solde += montant;
        System.out.println("Montant crédité : " + montant + ", Nouveau solde : " + solde);
    }


    @Override
    public double lireSolde() throws RemoteException {
        return solde;
    }
}
