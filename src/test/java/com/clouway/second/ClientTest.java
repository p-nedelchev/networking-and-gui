package com.clouway.second;

import com.clouway.second.client.Client;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ClientTest {
    private Client client;
    private InetAddress address;
    private FakeServer fakeServer;
    private Date time;
    private String messageSentByServer;



    @Before
    public void setUp() throws Exception {
        address = InetAddress.getByName("127.0.0.1");
        client = new Client(address, 15000);
        messageSentByServer = "Hello! ";
        CalendarUtil calendarUtil = new CalendarUtil() {
            @Override
            public String timeNow() {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, Calendar.SEPTEMBER, 28);
                time = calendar.getTime();
                messageSentByServer += time.toString();
                return time.toString();
            }
        };
        fakeServer = new FakeServer(15000, messageSentByServer, calendarUtil);
    }

    @Test
    public void happyPath() throws Exception {
        new Thread(client).start();
        fakeServer.sendMessage();
        String actualMessage = client.getLastReceivedMessage();
        assertThat(messageSentByServer, is(actualMessage));
    }
}
