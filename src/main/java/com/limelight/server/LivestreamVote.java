package com.limelight.server;
import java.util.*;

/**
 * Each viewer has a LivestreamVote object per Livestream.
 */
public class LivestreamVote {
    // The livestream object that this vote refers to
    private Livestream livestream;

    // The viewer can only vote (up or down) once and it cannot be undone
    private boolean alreadyVoted;

    /**
     * Create a LivestreamVote associated with a particular Livestream.
     * @param livestream the associated livestream.
     */
    public LivestreamVote(Livestream livestream) {
        this.livestream = livestream;
        alreadyVoted = false;
    }

    /**
     * Increase the possible livestream lifetime by 5 seconds.
     * Only if the viewer has not voted previously.
     */
    public void upVote() {
        if(!alreadyVoted) {
            livestream.getTimer().addSecondsToLivestream(5);
            alreadyVoted = true;
        }
    }

    /**
     * Decrease the possible livestream lifetime by 2 seconds.
     * Only if the viewer has not voted previously.
     */
    public void downVote() {
        if(!alreadyVoted) {
            livestream.getTimer().decreaseSecondsFromLivestream(2);
            alreadyVoted = true;
        }
    }

    /**
     * Returns the livestream.
     * @return livestream
     */
    public Livestream getLivestream() {return livestream;}

    /**
     * Returns whether the viewer has already voted, up or down.
     * @return alreadyVoted
     */
    public boolean getAlreadyVoted() {return alreadyVoted;}
}