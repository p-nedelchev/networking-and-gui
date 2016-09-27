package com.clouway.second.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Server implements Runnable {
    private int port;
    private boolean flag;
    private ServerSocket server = null;
    private String lastSentMessage;

    public Server(int port) {
        this.port = port;
        this.flag = true;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (flag) {
            Socket client = null;
            try {
                client = server.accept();
                sendMessage(client);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage (Socket client) {
        lastSentMessage = "Hello! " + Calendar.getInstance().getTime().toString();
        synchronized (this) {
            try (OutputStream out = client.getOutputStream()) {
                out.write(lastSentMessage.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.flag = false;
    }

    public String getLastMessage() {
        return this.lastSentMessage;
    }
}
