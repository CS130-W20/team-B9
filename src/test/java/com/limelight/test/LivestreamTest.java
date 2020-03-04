package com.limelight.test;

import com.limelight.server.StreamTimer;
import com.limelight.server.User;
import com.limelight.server.Livestream;
import com.limelight.server.LivestreamVote;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test Livestream class.
 */
public class LivestreamTest {
    private User user;
    private Livestream livestream;

    /**
     * Initializes user and livestream objects.
     */
    @Before
    public void setup() {
        user = new User();
        livestream = new Livestream(user.getUserName());
    }

    /**
     * Tests accessing the StreamTimer from Livestream and checks its initialized values.
     */
    @Test
    public void testLivestreamTimer() throws InterruptedException {
        Thread.sleep(1000); // Sleep 1 second
        StreamTimer streamTimer = livestream.getTimer();
        assert(streamTimer.getSecondsPastDuringLivestream() >= 1);
        assert(streamTimer.getSecondsLeftOfLivestream() <= 59);
    }

    /**
     * Tests upVote() functionality, increasing secondsLeftOfLivestream.
     */
    @Test
    public void testUpVote() {
        StreamTimer streamTimer = livestream.getTimer();
        long secondsPastDuringLivestreamBeforeUpvote = streamTimer.getSecondsPastDuringLivestream();
        long secondsLeftOfLivestreamBeforeUpvote = streamTimer.getSecondsLeftOfLivestream();
        livestream.upvote();
        long secondsPastDuringLivestreamAfterUpvote = streamTimer.getSecondsPastDuringLivestream();
        long secondsLeftOfLivestreamAfterUpvote = streamTimer.getSecondsLeftOfLivestream();
        assert(secondsLeftOfLivestreamAfterUpvote - secondsLeftOfLivestreamBeforeUpvote == 5);
        assert(secondsPastDuringLivestreamAfterUpvote == secondsPastDuringLivestreamBeforeUpvote);
    }

    /**
     * Tests downVote() functionality, decreasing secondsLeftOfLivestream.
     */
    @Test
    public void testDownVote() {
        StreamTimer streamTimer = livestream.getTimer();
        long secondsPastDuringLivestreamBeforeDownvote = streamTimer.getSecondsPastDuringLivestream();
        long secondsLeftOfLivestreamBeforeDownvote = streamTimer.getSecondsLeftOfLivestream();
        livestream.downvote();
        long secondsPastDuringLivestreamAfterDownvote = streamTimer.getSecondsPastDuringLivestream();
        long secondsLeftOfLivestreamAfterDownvote = streamTimer.getSecondsLeftOfLivestream();
        assert(secondsLeftOfLivestreamAfterDownvote - secondsLeftOfLivestreamBeforeDownvote == -2);
        assert(secondsPastDuringLivestreamAfterDownvote == secondsPastDuringLivestreamBeforeDownvote);
    }

}
