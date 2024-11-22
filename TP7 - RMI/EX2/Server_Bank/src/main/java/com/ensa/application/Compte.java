package com.ensa.application;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Compte extends Remote {
    void debiter(double montant) throws RemoteException;
    void crediter(double montant) throws RemoteException;
    double lireSolde() throws RemoteException;
}
