package com.clouway.third;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Server implements Runnable{
    private Set<Worker> clients;
    private int port;
    private boolean flag;
    private ServerSocket server = null;
    private String clientName;
    private OutputStream out;
    private Socket client;


    public Server(int port) {
        this.port = port;
        this.flag = true;
        clients = new HashSet<>();

    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while (flag) {
                client = server.accept();
                clientName = "client" + clients.size();
                Worker worker = new Worker(clients, client);
                clients.add(worker);
                System.out.println("Client number " + clients.size() + " has been connected\n");
                worker.start();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void stop() {
        this.flag = false;
    }

}
