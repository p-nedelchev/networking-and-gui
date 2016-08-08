package com.clouway.task3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Client {

    private Socket client = null;
    private BufferedReader in = null;
    private PrintStream out = null;
    private String fromServer, toServer;
    private Scanner sc = null;
    boolean flag = true;

    public void connect(String host, int port) throws IOException, NoSocketException {
        client = new Socket(host,port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        sc = new Scanner(System.in);
        out = new PrintStream(client.getOutputStream(), true);

        new Thread(){
            @Override
            public void run() {
                while(flag){
                    try {
                        readFromServer();
                    } catch (IOException e) {
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while(flag){
                    writeToServer();
                }
            }
        }.start();
        while(in.read() > (-1)){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        in.close();
        out.close();
        flag = false;
        throw new NoSocketException();
    }

    private void readFromServer() throws IOException {
        if((fromServer = in.readLine()) != null) {
            System.out.println("From server: " + fromServer);
        }
    }

    private void writeToServer(){
        toServer = sc.nextLine();
        out.println(toServer);
    }

}
