package com.clouway.second;

import com.clouway.second.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Petar Nedelchev <peter.krasimirov@gmail.com>
 */
public class ServerTest {
    private Server server;
    private InetAddress localhost;
    private FakeClient fakeClient;
    private Date time;



    @Before
    public void setUp() throws Exception {
        localhost = InetAddress.getByName("127.0.0.1");
        server = new Server(15000, new CalendarUtil() {
            @Override
            public String timeNow() {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2006,Calendar.JULY,27);
                time = calendar.getTime();
                return time.toString();
            }
        });
        fakeClient = new FakeClient(15000, localhost);
        new Thread(server).start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void happyPath() throws Exception {
        String actualMessage = fakeClient.readServerMessage();
        String expectedMessage = "Hello! " + time.toString();
        assertThat(actualMessage, is(expectedMessage));
    }


}
