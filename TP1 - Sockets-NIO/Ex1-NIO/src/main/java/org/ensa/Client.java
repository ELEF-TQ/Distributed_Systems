package org.ensa;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
public class Client {
    public static void main(String[] args)
    {
        Scanner clavier = new Scanner(System.in);
        try {
            SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 8089));
            System.out.println("Connection accepted by the Server..");
            System.out.println("Saisir le message à envoyer au serveur");
            String msg = clavier.nextLine();
            ByteBuffer buffer=ByteBuffer.allocate(1024); // create an empty buffer
            buffer.put(msg.getBytes());
            buffer.flip(); // preparer le buffer en mode read
            int bytesWritten = client.write(buffer);
            System.out.println(String.format("Sending Message: %s\nbufforBytes: %d",msg, bytesWritten));
            buffer.clear(); // préparer le buffer en mode write;
            client.read(buffer);// Reads a sequence of bytes from this channel into the given buffer
// Parse data from buffer to String
            String data = new String(buffer.array()).trim();
            System.out.println("Received message from server: " + data);
            client.close();
            System.out.println("Client connection closed"); }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}