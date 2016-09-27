package com.clouway.second.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Client implements Runnable {
    private int port;
    private Socket client;
    private InetAddress address;
    private boolean flag;


    public Client (InetAddress address, int port) {
        this.port = port;
        this.address = address;
        this.flag = true;
    }

    @Override
    public void run() {
        try {
            client = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (flag) {
            receiveMessage(client);
            stop();
        }
    }

    private void receiveMessage(Socket client) {
        synchronized (this) {
            try (InputStream in = client.getInputStream()) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = in.read(buffer)) != -1) {
                    System.out.println("Server said: " + new String(buffer, 0, count));
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stop() {
        this.flag = false;
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed. Client is closing...");
    }


}
