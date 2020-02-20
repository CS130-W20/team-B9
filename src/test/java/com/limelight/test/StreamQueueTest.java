package com.limelight.test;

import com.limelight.server.User;
import com.limelight.server.StreamQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamQueueTest {
    private User user1, user2, user3, user4, user5;
    private StreamQueue queue;

    /**
     * sets up test users and adds them to queue
     */
    @Before
    public void setup() {
        user1 = new User();
        user1.setUserName("user1");
        user2 = new User();
        user3 = new User();
        user3.setUserName("user3");
        user4 = new User();
        queue = StreamQueue.getInstance();
        queue.addStreamer(user1);
        queue.addStreamer(user3);
        queue.addStreamer(user4);
        queue.addStreamer(user2);
    }

    /**
     * creates Queue()
     */
    @Test
    public void createQueue() {
        assertNotNull(queue.getStreamers());
    }

    /**
     * tests StreamQueue.addStreamer(User u)
     */
    @Test
    public void testJoin() {
        user5 = new User();
        assertTrue(queue.addStreamer(user5));
        assertFalse(queue.addStreamer(user2));
    }

    /**
     * tests StreamQueue.removeStreamer(User u)
     */
    @Test
    public void testLeave() {
        queue.removeStreamer(user1);
        assertTrue(queue.addStreamer(user1));
    }

    /**
     * tests StreamQueue.nextStreamer()
     */
    @Test
    public void testSelect() {
        assertEquals(queue.nextStreamer().getUserName(), "user3");
    }

}
