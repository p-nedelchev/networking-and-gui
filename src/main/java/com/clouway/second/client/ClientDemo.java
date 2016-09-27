package com.clouway.second.client;


import com.clouway.second.server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ClientDemo {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            Client client = new Client(address, 15000);
            new Thread(client).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
