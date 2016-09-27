package com.clouway.second;

import com.clouway.second.client.Client;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ClientTest {
    private Client client;
    private InetAddress address;

    @Before
    public void setUp() throws Exception {
        address = InetAddress.getByName("127.0.0.1");
        client = new Client(address, 15000);
    }

    @Test
    public void happyPath() throws Exception {


    }
}
