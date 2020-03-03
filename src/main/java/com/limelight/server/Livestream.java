package com.limelight.server;
import java.util.*;

/**
 * Associated with each new livestreamer beginning a livestream.
 */
public class Livestream {
    // The User who is livestreaming
    private User user;

    // Timer for the livestream
    private StreamTimer streamTimer;

    private int voteCount;

    /**
     * Creates a new livestream associated with the user streaming and a new StreamTimer.
     * @param user the user who is livestreaming content.
     */
    public Livestream(User user) {
        this.user = user;
        voteCount = 0;
        streamTimer = new StreamTimer();
    }

    /**
     * Returns the StreamTimer for the livestream.
     * @return streamTimer
     */
    public StreamTimer getTimer() {
    	return streamTimer;
    }

    /**
     * Returns the user who is livestreaming.
     * @return user
     */
    public User getUser() {
    	return user;
    }

    /**
     * Process the upvote for the livestream.
     * Voting buttons should be disabled after a vote occurs.
     */
    public void upvote() {
        voteCount++;
        streamTimer.addSecondsToLivestream(5);
    }

    /**
     * Process the downvote for the livestream.
     * Voting buttons should be disabled after a vote occurs.
     */
    public void downvote() {
        voteCount--;
        streamTimer.decreaseSecondsFromLivestream(2);
    }

    /**
     * Returned the net votes for the livestream.
     * @return voteCount
     */
    public int getVoteCount() {
        return voteCount;
    }
    
    // TODO: Add functionality that livestream stops when streamTimer.secondHasPassed() returns false

}