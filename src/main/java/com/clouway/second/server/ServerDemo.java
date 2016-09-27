package com.clouway.second.server;

import java.net.InetAddress;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerDemo {
    public static void main(String[] args) {
        Server server = new Server(15000);
        new Thread(server).start();
    }
}
