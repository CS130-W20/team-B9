package com.limelight.server;
import java.util.*;

public class StreamTimer {
    //usage: Each live stream session has a StreamTimer

    // Maximum amount of time a livestream can last
    private static long maximumLivestreamSeconds = 180; 

    // Minimum amount of time a livestream can last
    private static long minimumLivestreamSeconds = 60;

    // The number of seconds the livestream has lasted already
    private long secondsPastDuringLivestream; 

    // The number of seconds left that the livestream will last
    private long secondsLeftOfLivestream;

    public StreamTimer() {
    	secondsPastDuringLivestream = 0;
    	secondsLeftOfLivestream = 60;
    }

    // Returns true if the livestream still has time left
    // Returns false if livestream is out of time
    public boolean secondHasPassed() {
    	secondsPastDuringLivestream++;
    	secondsLeftOfLivestream--;
    	if(secondsLeftOfLivestream <= 0 && secondsPastDuringLivestream < minimumLivestreamSeconds) {
    		secondsLeftOfLivestream = minimumLivestreamSeconds - secondsPastDuringLivestream;
    	}
    	else if(secondsLeftOfLivestream <= 0 || secondsPastDuringLivestream >= maximumLivestreamSeconds) {
    		return false;
    	}
    	return true;
    }

    public long getSecondsPastDuringLivestream() {
    	return secondsPastDuringLivestream;
    }

    public long getSecondsLeftOfLivestream() {
    	return secondsLeftOfLivestream;
    }

    public void addSecondsToLivestream(long seconds) {
    	secondsLeftOfLivestream += seconds;
    }

    public void decreaseSecondsFromLivestream(long seconds) {
    	secondsLeftOfLivestream -= seconds;
    }  

}