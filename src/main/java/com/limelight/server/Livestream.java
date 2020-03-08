package com.limelight.server;
import java.util.*;

/**
 * Associated with each new livestreamer beginning a livestream.
 */
public class Livestream {
    // The User who is livestreaming
    private String user;

    // Timer for the livestream
    private StreamTimer streamTimer;

    private int voteCount;

    private List<LivestreamComment> comments;

    /**
     * Creates a new livestream associated with the user streaming and a new StreamTimer.
     * @param user the user who is livestreaming content.
     */
    public Livestream(String user) {
        this.user = user;
        voteCount = 0;
        streamTimer = new StreamTimer();
        comments = new ArrayList<>();
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
    public String getUser() {
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

    /**
     * Add a comment to the list of comments on the current stream.
     * @param username of commenter
     * @param comment content
     */
    public void addComment(String username, String comment) {
        comments.add(new LivestreamComment(username, comment));
    }

    public void clearComments() {
        comments.clear();
    }
    /**
     * Get a list of comments on the current stream.
     * @return list of comments on the current stream
     */
    public List<LivestreamComment> getComments() {
        return comments;
    }
    
    // TODO: Add functionality that livestream stops when streamTimer.secondHasPassed() returns false

}