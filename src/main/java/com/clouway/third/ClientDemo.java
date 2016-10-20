package com.clouway.third;

import com.clouway.second.client.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ClientDemo {
    public static void main(String[] args) {
        try {
            Client client  = new Client(InetAddress.getByName("127.0.0.1"), 8155, System.in);

            new Thread(client).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
