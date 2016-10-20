package com.clouway.third;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.Set;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
class Worker extends Thread {
    private Set<Worker> clients;
    private Socket client;

    public Worker(Set<Worker> clients, Socket client){
        this.client = client;
        this.clients = clients;
    }

    @Override
    public void run() {
        OutputStream out;
        InputStream in;
        BufferedReader reader;
         try {
             reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
             notifyClients();
             receiveMessage(reader);
        } catch (SocketException e) {
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage(BufferedReader reader) throws IOException {
        String message;
        while((message = reader.readLine()) != null) {
            System.out.println(message);
        }
    }

    private void notifyClients() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clients.forEach(worker -> {
                    try {
                        worker.sendMessage("Client number " + clients.size() + " connected\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    private void sendMessage(String message) throws IOException {

        client.getOutputStream().write(message.getBytes());
    }

}
