package com.clouway.third;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class FakeClient implements Runnable{
    private int port;
    private Socket client;
    private InetAddress address;
    private boolean flag;
    private PrintWriter out = null;
    private InputStream in = null;
    private BufferedReader reader = null;
    private InputStream input;
    private String receivedMessage;
    private BlockingQueue<String> queue;

    public FakeClient(InetAddress address, int port, InputStream input, BlockingQueue<String> queue) {
        this.address = address;
        this.port = port;
        this.input = input;
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            client = new Socket(address, port);
            out = new PrintWriter(client.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            sendMessage();
            receiveMessage();
            queue.put("new");
            } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        Scanner scanner = new Scanner(input);
        if(scanner.hasNextLine()) {
            out.write(scanner.nextLine()+"\n");
        }
    }

    public void receiveMessage() throws IOException {
        receivedMessage = reader.readLine();
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }


}
