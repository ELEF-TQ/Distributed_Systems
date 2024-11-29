package com.ensa.classes;

import com.ensa.classes.ClientLocal;
import com.ensa.interfaces.ClientDistantInterface;

import java.rmi.RemoteException;

public class ClientDistant extends ClientLocal implements ClientDistantInterface {

    public ClientDistant(String id, String name) throws RemoteException {
        super(id, name);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String getNameDistant() throws RemoteException {
        return super.name;
    }

    @Override
    public String getIdDistant() throws RemoteException {
        return super.id;
    }
}
