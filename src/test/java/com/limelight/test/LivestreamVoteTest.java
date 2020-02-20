package com.limelight.test;

import com.limelight.server.StreamTimer;
import com.limelight.server.User;
import com.limelight.server.Livestream;
import com.limelight.server.LivestreamVote;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LivestreamVoteTest {
    private User user;
    private Livestream livestream;
    private LivestreamVote livestreamVote;

    @Before
    public void setUp() {
        user = new User();
        livestream = new Livestream(user);
        livestreamVote = new LivestreamVote(livestream);
    }

    @Test
    public void testLivestreamTimer() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 60);
    }

    @Test
    public void testUpVote() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        livestreamVote.upVote();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 65);
        assertTrue(livestreamVote.getAlreadyVoted());
    }

    @Test
    public void testDownVote() {
        StreamTimer streamTimer = livestreamVote.getLivestream().getTimer();
        livestreamVote.downVote();
        assert(streamTimer.getSecondsPastDuringLivestream() == 0);
        assert(streamTimer.getSecondsLeftOfLivestream() == 58);
        assertTrue(livestreamVote.getAlreadyVoted());
    }
}