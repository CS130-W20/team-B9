package com.limelight.server;
import java.util.*;

public class LivestreamVote {
    //usage: Each viewer has a LivestreamVote object per livestreamer
    
    // The livestream object that this vote refers to
    private Livestream livestream;

    // The viewer can only vote (up or down) once and it cannot be undone
    private boolean alreadyVoted;

    public LivestreamVote(Livestream livestream) {
        this.livestream = livestream;
        alreadyVoted = false;
    }

    // Increase livestream by 5 seconds
    public void upVote() {
        if(!alreadyVoted) {
            livestream.getTimer().addSecondsToLivestream(5);
            alreadyVoted = true;
        }
    }

    // Decrease livestream by 2 seconds
    public void downVote() {
        if(!alreadyVoted) {
            livestream.getTimer().decreaseSecondsFromLivestream(2);
            alreadyVoted = true;
        }
    }

    public Livestream getLivestream() {return livestream;}

    public boolean getAlreadyVoted() {return alreadyVoted;}
}