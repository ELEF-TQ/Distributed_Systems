package org.ensa;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) {
        try {
            // Open connection to server
            SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 8089));
            System.out.println("Connected to the server...");

            Scanner scanner = new Scanner(System.in);
            String message;

            while (true) {
                // Read welcome message
                System.out.println(readMessage(client));

                System.out.println("Enter your message (type 'bye' to exit):");
                message = scanner.nextLine();

                // Send message to server
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                client.write(buffer);

                // Read server response
                System.out.println(readMessage(client));

                // Exit if message is "bye"
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            client.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to read message from server
    private static String readMessage(SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        return new String(buffer.array()).trim();
    }
}
