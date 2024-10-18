package org.ensa;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
public class NIOServer {
    private static Selector selector = null;
    private static void handleAccept(ServerSocketChannel mySocket, SelectionKey key) throws IOException {
        System.out.println("Connection Accepted..");
        SocketChannel client = mySocket.accept(); // Accept the connection
        client.configureBlocking(false); // set non-blocking mode
// Register that client is reading this channel
        client.register(selector, SelectionKey.OP_READ);
    }
    private static void handleRead(SelectionKey key) throws IOException
    { System.out.println("Reading client's message.");
// // Returns the channel for which this key was created.
        SocketChannel client = (SocketChannel)key.channel();
// Create buffer to read data
        ByteBuffer buffer = ByteBuffer.allocate(1024); // create an empty buffer
        client.read(buffer); // Reads a sequence of bytes from this channel into the given buffer
// Parse data from buffer to String
        String data = new String(buffer.array()).trim();
        System.out.println("Received message: " + data);
        System.out.println("writing a message to client");
        buffer.clear(); // préparer le buffer en mode write
        buffer.put(new String ("Bonjour Mr client").getBytes());
        buffer.flip(); //préparer le buffer en mode read
        client.write(buffer); // Writes a sequence of bytes to this channel from the given buffer
        client.close();
    }
    public static void main(String[] args) {
        try {
            selector = Selector.open();

            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            ServerSocket serverSocket=serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress("localhost", 8089));
            serverSocketChannel.configureBlocking(false);
            int ops = serverSocketChannel.validOps();
            serverSocketChannel.register(selector, ops,null);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys=selector.selectedKeys();
                Iterator<SelectionKey> i= selectedKeys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    if (key.isAcceptable()) {
                        handleAccept(serverSocketChannel,key); // New client has been accepted
                    }
                    else if (key.isReadable()) {
// We can run non-blocking operation
// READ on our client
                        handleRead(key);
                    }
                    i.remove();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}