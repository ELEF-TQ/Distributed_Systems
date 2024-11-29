

import com.ensa.interfaces.ChatServerInterface;
import com.ensa.interfaces.ClientDistantInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

    private final List<ClientDistantInterface> clients;

    protected ChatServer() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
    }

    @Override
    public synchronized void signIn(ClientDistantInterface client) throws RemoteException {
        // Ajout du client à la liste
        clients.add(client);
        System.out.println("Client connected: " + client.getNameDistant());
        // Notification à tous les autres clients
        sendToAll("Server", client.getNameDistant() + " has joined the chat!");
    }

    @Override
    public synchronized void signOut(ClientDistantInterface client, String message) throws RemoteException {
        // Retirer le client de la liste
        clients.remove(client);
        System.out.println("Client disconnected: " + client.getNameDistant());
        // Notification à tous les autres clients
        sendToAll("Server", client.getNameDistant() + " has left the chat: " + message);
    }

    @Override
    public synchronized void sendToAll(String senderName, String message) throws RemoteException {
        for (ClientDistantInterface client : clients) {
            try {
                client.receiveMessage(senderName + ": " + message);
            } catch (RemoteException e) {
                System.out.println("Error sending message to a client: " + client.getNameDistant());
            }
        }
    }

    @Override
    public synchronized void sendToOne(String senderName, String receiverName, String message) throws RemoteException {
        boolean found = false;
        for (ClientDistantInterface client : clients) {
            if (client.getNameDistant().equals(receiverName)) {
                try {
                    client.receiveMessage(senderName + " (private): " + message);
                    found = true;
                    break;
                } catch (RemoteException e) {
                    System.out.println("Error sending private message to " + receiverName);
                }
            }
        }
        if (!found) {
            System.out.println("Client with name '" + receiverName + "' not found.");
        }
    }
}
