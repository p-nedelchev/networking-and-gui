package com.clouway.third;

import com.clouway.second.server.*;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerDemo {
    public static void main(String[] args) {
        Server server  = new Server(8155);
        new Thread(server).start();
    }
}
