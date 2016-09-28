package com.clouway.second.server;

import com.clouway.second.CalendarUtil;

import java.net.InetAddress;
import java.util.Calendar;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerDemo {
    public static void main(String[] args) {
        Server server = new Server(15000, new CalendarUtil() {
            @Override
            public String timeNow() {
                return Calendar.getInstance().getTime().toString();
            }
        });
        new Thread(server).start();
    }
}
