package com.limelight.test;

import com.limelight.server.StreamTimer;
import com.limelight.server.User;
import com.limelight.server.Livestream;
import com.limelight.server.LivestreamVote;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test LivestreamVote functionality.
 */
public class LivestreamVoteTest {
    private User user;
    private Livestream livestream;
    private LivestreamVote livestreamVote;

    /**
     * Initializes user, livestream, livestreamVote objects.
     */
    @Before
    public void setup() {
        user = new User();
        user.setUserName("user");
        livestream = new Livestream(user.getUserName());
        livestreamVote = new LivestreamVote(livestream);
    }

    /**
     * Tests accessing the StreamTimer from Livestream and checks its initialized values.
     */
    @Test
    public void testLivestreamTimer() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 60);
    }

    /**
     * Tests upVote() functionality, increasing secondsLeftOfLivestream.
     */
    @Test
    public void testUpVote() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        livestreamVote.upVote();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 65);
        assertTrue(livestreamVote.getAlreadyVoted());
    }

    /**
     * Tests downVote() functionality, decreasing secondsLeftOfLivestream.
     */
    @Test
    public void testDownVote() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        livestreamVote.downVote();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 58);
        assertTrue(livestreamVote.getAlreadyVoted());
    }
}