package com.ensa.application;

public interface Hello extends java.rmi.Remote {
    public String sayHello() throws java.rmi.RemoteException;
}
