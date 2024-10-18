package org.ensa;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private static Selector selector;

    // Handle new client connection
    private static void handleAccept(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        System.out.println("Connection Accepted: " + client.getRemoteAddress());

        // Register client for reading
        client.register(selector, SelectionKey.OP_READ);

        // Send welcome message
        sendMessage(client, "Welcome to the NIO Server!\n");
    }

    // Handle reading from client
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = client.read(buffer);

        if (bytesRead == -1) {
            client.close();
            System.out.println("Client disconnected.");
            return;
        }

        String message = new String(buffer.array()).trim();
        System.out.println("Message received: " + message);

        if (!message.equalsIgnoreCase("bye")) {
            // Send date and time
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            sendMessage(client, "Current Date and Time: " + dateTime + "\n");

            // Convert to uppercase and send back
            String upperCaseMessage = message.toUpperCase();
            sendMessage(client, "Uppercase: " + upperCaseMessage + "\n");
        } else {
            sendMessage(client, "Goodbye!\n");
            client.close();
        }
    }

    // Send message to the client
    private static void sendMessage(SocketChannel client, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        client.write(buffer);
    }

    public static void main(String[] args) {
        try {
            // Step 1: Open Selector
            selector = Selector.open();

            // Step 2: Bind the Server Port
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8089));

            // Step 3: Register ServerSocket to Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server is listening on port 8089...");

            // Step 4: Wait for events
            while (true) {
                selector.select(); // Blocks until an event is available

                // Step 5: Get selection keys
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAccept(serverSocketChannel); // Step 6: Accept client
                    } else if (key.isReadable()) {
                        handleRead(key); // Step 7: Read client message
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
