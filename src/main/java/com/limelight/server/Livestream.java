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

    /**
     * Creates a new livestream associated with the user streaming and a new StreamTimer.
     * @param user the user who is livestreaming content.
     */
    public Livestream(User user) {
        this.user = user;
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
    
    // TODO: Add functionality that livestream stops when streamTimer.secondHasPassed() returns false
}