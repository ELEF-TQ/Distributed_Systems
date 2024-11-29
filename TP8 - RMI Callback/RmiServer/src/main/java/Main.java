

import com.ensa.interfaces.ChatServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(ChatServerInterface.LOOKUP_NAME, server);
            System.out.println("Chat server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}