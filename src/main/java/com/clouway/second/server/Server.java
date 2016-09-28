package com.clouway.second.server;



import com.clouway.second.CalendarUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class Server implements Runnable {
    private int port;
    private CalendarUtil calendarUtil;
    private boolean flag;
    private ServerSocket server = null;

    public Server(int port, CalendarUtil calendarUtil) {
        this.port = port;
        this.calendarUtil = calendarUtil;
        this.flag = true;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (flag) {
            Socket client = null;
            try {
                client = server.accept();
                sendMessage(client);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage (Socket client) {
        String sentMessage = "Hello! " + calendarUtil.timeNow();
        try (OutputStream out = client.getOutputStream()) {
            out.write(sentMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.flag = false;
    }

}
