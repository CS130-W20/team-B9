package com.limelight.test;

import com.limelight.server.User;
import com.limelight.server.StreamQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class of tests that tests StreamQueue functionality.
 */
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
        user2.setUserName("user2");
        user3 = new User();
        user3.setUserName("user3");
        user4 = new User();
        user4.setUserName("user4");
        queue = StreamQueue.getInstance();
        queue.addStreamer(user1.getUserName());
        queue.addStreamer(user3.getUserName());
        queue.addStreamer(user4.getUserName());
        queue.addStreamer(user2.getUserName());
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
        user5.setUserName("user5");
        assertTrue(queue.addStreamer(user5.getUserName()));
        assertFalse(queue.addStreamer(user2.getUserName()));
    }

    /**
     * tests StreamQueue.removeStreamer(User u)
     */
    @Test
    public void testLeave() {
        queue.removeStreamer(user1.getUserName());
        assertTrue(queue.addStreamer(user1.getUserName()));
    }

    /**
     * tests StreamQueue.nextStreamer()
     */
    /*@Test
    public void testSelect() {
        assertEquals(queue.nextStreamer().getUserName(), "user3");
    }*/

    /**
     * tests StreamQueue.getGetCurrentStreamer() and makes sure that current
     * streamer is the same
     */
    @Test
    public void testGetCurrentStreamer() {
        assertEquals(queue.getCurrentStreamer(), queue.getCurrentStreamer());
    }

    /**
     * tests that the queue will remove the current user
     */
    @Test
    public void testPollStreamer() {
        queue.pollStreamer();
        assertTrue(queue.addStreamer(user3.getUserName()));
    }

}
