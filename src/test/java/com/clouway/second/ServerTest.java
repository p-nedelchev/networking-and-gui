package com.clouway.second;

import com.clouway.second.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerTest {
    private Server server;
    private InetAddress localhost;
    private FakeClient client;

    private class FakeClient {
        private int port;
        private InetAddress address;

        private FakeClient(int port, InetAddress address) {
            this.port = port;
            this.address = address;
        }

        private String readServerMessage() {
            StringBuilder builder = new StringBuilder();
            try (Socket client = new Socket(address, port);
                 InputStream in = client.getInputStream()) {
                byte[] buffer = new byte[1024];
                int count;
                while((count = in.read(buffer)) != -1) {
                    builder.append(new String(buffer, 0, count));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
    }

    @Before
    public void setUp() throws Exception {
        localhost = InetAddress.getByName("127.0.0.1");
        server = new Server(15000);
        client = new FakeClient(15000, localhost);
    }

    @Test
    public void happyPath() throws Exception {
        new Thread(server).start();
        String expectedMessage = client.readServerMessage();
        String actualMessage = server.getLastMessage();
        assertThat(actualMessage, is(expectedMessage));
    }
}
