package com.limelight.test;

import com.limelight.server.StreamTimer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test StreamTimer functionality.
 */
public class StreamTimerTest {
    private StreamTimer streamTimer;

    /**
     * Initialize streamTimer object.
     */
    @Before
    public void setup() {
        streamTimer = new StreamTimer();
    }

    /**
     * Tests that streamTimer functions work as expected.
     */
    @Test
    public void testStreamTimer() throws InterruptedException {
        Thread.sleep(1000); // Sleep for 1 second
        assert(streamTimer.getSecondsLeftOfLivestream() <=59);
        assert(streamTimer.getSecondsPastDuringLivestream() >= 1);

        Thread.sleep(2000); // Sleep for 2 seconds
        assert(streamTimer.getSecondsLeftOfLivestream() <= 57);
        assert(streamTimer.getSecondsPastDuringLivestream() >= 3);
    }



}