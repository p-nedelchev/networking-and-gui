package com.clouway.third;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ClientTest {
    private FakeServer fakeServer;
    private Client client;
    private BlockingQueue<String> queue;
    private String clientMessage;

    @Before
    public void setUp() throws Exception {
        queue = new LinkedBlockingDeque<>();
        int port = (new Random().nextInt(62000 - 1024) + 1024);
        clientMessage = "Hello";
        InputStream in = new ByteArrayInputStream(clientMessage.getBytes());
        fakeServer = new FakeServer(port, queue);
        client = new Client(InetAddress.getByName("127.0.0.1"), port, in);
        new Thread(fakeServer).start();
        new Thread(client).start();
    }

    @After
    public void tearDown() throws Exception {
        fakeServer.stop();

    }

    @Test
    public void clientReceiveMessage() throws Exception {
        queue.take();
        String actual = "Hello client";
        String expected = client.getReceivedMessage();
        assertThat(actual, is(expected));
    }

    @Test
    public void clientSendMessage() throws Exception {
        queue.take();
        String expected = fakeServer.getMessage();
        assertThat(clientMessage, is(expected));
    }
}
