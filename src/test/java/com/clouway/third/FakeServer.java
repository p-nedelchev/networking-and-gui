package com.clouway.third;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class FakeServer implements Runnable{
    private int port;
    private BlockingQueue<String> queue;
    private boolean flag;
    private ServerSocket server;
    private String messageFromClient;

    public FakeServer(int port, BlockingQueue<String> queue) {
        this.port = port;
        this.queue = queue;
        this.flag = true;
    }

    @Override
    public void run() {

        try {
            server = new ServerSocket(port);
            while (flag) {
                Socket client = server.accept();
                PrintWriter output = new PrintWriter(client.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String messageToClient = "Hello client";
                output.write(messageToClient);
                while (flag) {
                    messageFromClient = reader.readLine();
                    queue.put("new");
                }
            }
            }catch(IOException | InterruptedException e){
                e.printStackTrace();
            }
    }

    public String getMessage() {
        return messageFromClient;
    }

    public void stop() {
        try {
            flag = false;
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.flag = false;
    }
}
