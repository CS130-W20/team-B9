package com.limelight.test;

import com.limelight.server.StreamTimer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamTimerTest {
    private StreamTimer streamTimer;

    @Before
    public void setUp() {
        streamTimer = new StreamTimer();
    }

    @Test
    public void testStreamTimer() {
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 60);
        assertTrue(streamTimer.secondHasPassed());
        assert(streamTimer.getSecondsPastDuringLivestream() == 1);
        assert(streamTimer.getSecondsLeftOfLivestream() == 59);

        // Livestream has not lasted for 60 seconds yet, reset secondsLeft to 60 - secondsPast
        streamTimer.decreaseSecondsFromLivestream(59);
        assertTrue(streamTimer.secondHasPassed());
        assert(streamTimer.getSecondsPastDuringLivestream() == 2);
        assert(streamTimer.getSecondsLeftOfLivestream() == 58);

        // Livestream runs out of time after 60 seconds
        boolean timeLeft = true;
        for(int i = 0; i < 58 ; i++) {
            timeLeft = streamTimer.secondHasPassed();
        }
        assertFalse(timeLeft);
    }

}