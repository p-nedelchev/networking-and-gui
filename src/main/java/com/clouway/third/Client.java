package com.clouway.third;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Client implements Runnable {
    private int port;
    private Socket client;
    private InetAddress address;
    private boolean flag;
    private PrintWriter out = null;
    private InputStream in = null;
    private BufferedReader reader = null;
    private InputStream input;
    private String receivedMessage;



    public Client(InetAddress address, int port, InputStream input) {
        this.port = port;
        this.address = address;
        this.input = input;
        this.flag = true;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(input);
        try {
            client = new Socket(address, port);
            out = new PrintWriter(client.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            receiveMessage(reader);
            while (flag) {
                if(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    out.write(line + "\n");
                    out.flush();
                 }
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage( BufferedReader reader) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        while((receivedMessage = reader.readLine()) != null) {
                            System.out.println(receivedMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stop() {
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

    public void setInput(InputStream input) {
        this.input = input;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }
}

