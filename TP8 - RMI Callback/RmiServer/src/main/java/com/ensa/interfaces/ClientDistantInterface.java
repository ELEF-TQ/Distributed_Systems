package com.ensa.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientDistantInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
    String getNameDistant() throws RemoteException;
    String getIdDistant() throws RemoteException;
}
