package com.clouway.third;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerTest {
    private Server server;
    private FakeClient fakeClient;
    private String clientMessage;
    private BlockingQueue<String> queue;



    @Before
    public void setUp() throws Exception {
        queue = new LinkedBlockingQueue<String>();
        clientMessage = "Hello from fakeClient\n";
        InputStream in = new ByteArrayInputStream(clientMessage.getBytes());
        int port = (new Random().nextInt(62000 - 1024) + 1024);
        server = new Server(port);
        fakeClient = new FakeClient(InetAddress.getByName("127.0.0.1"),port, in, queue);
        new Thread(server).start();
        new Thread(fakeClient).start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    @Test
    public void serverSendsMessage() throws Exception {
        queue.take();
        String actual = fakeClient.getReceivedMessage();
        String expected = "Client number 1 connected";
        assertThat(actual, is(expected));
    }

    @Test
    public void serverSendsAllMessage() throws Exception {
        new Thread(fakeClient).start();
        queue.take();
        queue.take();
        String expected = "Client number 2 connected";
        String actual = fakeClient.getReceivedMessage();
        assertThat(actual, is(expected));
    }
}
