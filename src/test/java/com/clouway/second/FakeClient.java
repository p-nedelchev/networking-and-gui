package com.clouway.second;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class FakeClient {
    private int port;
    private InetAddress address;

    public FakeClient(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public String readServerMessage() {
        String message = "";
        try (Socket client = new Socket(address, port);
             InputStream in = client.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
