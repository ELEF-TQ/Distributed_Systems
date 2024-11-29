package com.ensa.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {
    String LOOKUP_NAME = "ChatServer";

    void sendToAll(String senderName, String message) throws RemoteException;
    void sendToOne(String senderName, String receiverName, String message) throws RemoteException;
    void signIn(ClientDistantInterface client) throws RemoteException;
    void signOut(ClientDistantInterface client, String message) throws RemoteException;
}
