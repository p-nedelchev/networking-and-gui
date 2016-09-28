package com.clouway.second.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private String lastReceivedMessage;


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
            lastReceivedMessage = receiveMessage(client);
            stop();
        }
    }

    private String receiveMessage(Socket client) {
            String lastMessage = "";
            try (InputStream in = client.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                lastMessage = reader.readLine();
                System.out.println(lastMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lastMessage;

    }

    private void stop() {
        this.flag = false;
        try {
            if(client != null) {
                this.client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed. Client is closing...");
    }

    public String getLastReceivedMessage() {
        return lastReceivedMessage;
    }
}
