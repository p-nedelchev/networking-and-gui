package com.clouway.second;

import com.clouway.second.server.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class FakeServer {
    private int port;
    private CalendarUtil calendar;
    private String dummyMessage;

    public FakeServer(int port, String dummyMessage, CalendarUtil calendarUtil) {
        this.port = port;
        this.calendar = calendarUtil;
        this.dummyMessage = dummyMessage;
    }

    public void sendMessage() {
        try(ServerSocket server = new ServerSocket(port);
            Socket client = server.accept()) {
            OutputStream out = client.getOutputStream();
            out.write((dummyMessage + calendar.timeNow()).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
