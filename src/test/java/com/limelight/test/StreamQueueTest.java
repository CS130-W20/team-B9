package com.limelight.test;

import com.limelight.server.User;
import com.limelight.server.StreamQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamQueueTest {
    private User user1, user2, user3, user4, user5;
    private StreamQueue queue;

    @Before
    public void setup() {
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        queue = StreamQueue.getInstance();
        queue.addStreamer(user1);
        queue.addStreamer(user3);
        queue.addStreamer(user4);
        queue.addStreamer(user2);
    }

    @Test
    public void createQueue() {
        assertNotNull(queue.getStreamers());
    }

    @Test
    public void testJoin() {
        user5 = new User();
        assertTrue(queue.addStreamer(user5));
        assertFalse(queue.addStreamer(user2));
    }

    @Test
    public void testLeave() {
        queue.removeStreamer(user3);
        assertTrue(queue.addStreamer(user3));
    }

    @Test
    public void testSelect() {
        User u1 = queue.nextStreamer();
        assertEquals(u1, user1);
    }

}
