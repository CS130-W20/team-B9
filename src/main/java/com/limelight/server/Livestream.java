package com.limelight.server;
import java.util.*;

public class Livestream {
    //usage: Each streamer is associated with a livestream object
    
    // The User who is livestreaming
    private User user;

    // Timer for the livestream
    private StreamTimer streamTimer;

    public Livestream(User user) {
        this.user = user;
        streamTimer = new StreamTimer();
    }

    public StreamTimer getTimer() {
    	return streamTimer;
    }

    public User getUser() {
    	return user;
    }
    
    // TODO: Add functionality that livestream stops when streamTimer.secondHasPassed() returns false
}