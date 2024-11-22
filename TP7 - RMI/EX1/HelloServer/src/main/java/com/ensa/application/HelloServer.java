package com.ensa.application;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class HelloServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            HelloImpl od= new HelloImpl("Hello World !");
            Naming.rebind("rmi://localhost:1099/BK", od);
            System.out.println("Serveur prêt");
        }
        catch (Exception e) {
            System.out.println("Serveur erreur :"+e);
        }}}