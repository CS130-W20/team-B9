package com.limelight.server;
import java.util.concurrent.*;
import java.util.*;

/**
 * StreamQueue is a singleton that manages the lineup of streamers for the application
 * It manages creating, joining, and leaving the queue
 * as well as returning the current streamer and removing that streamer from the queue
 * after their time runs out
 */
public class StreamQueue {
    //usage: StreamQueue queue = StreamQueue.getInstance();
    private static StreamQueue sq = new StreamQueue();
    public static StreamQueue getInstance() { return sq; }

    //usage: queue.getStreamers();
    private ConcurrentLinkedQueue<String> streamers = new ConcurrentLinkedQueue<String>();
    public ConcurrentLinkedQueue<String> getStreamers() { return streamers; }

    /**
     * add streamer to queue
     * @param u user being added to the stream
     * @return true if streamer was successfully added, false if already in queue
     */
    public boolean addStreamer(String u) {
        if (streamers.contains(u)) return false;
        streamers.add(u);
        return true;
    }

    /**
     * remove streamer from queue
     * @param u user being removed from stream
     * @return true if streamer was removed, false if streamer was not already in
     *          queue
     */
    public boolean removeStreamer(String u) {
        if (!streamers.contains(u)) return false;
        streamers.remove(u);
        return true;
    }

    /**
     * get next streamer
     * @return return true if there is a next streamer to be picked
     */
    /*public User nextStreamer() {
        return streamers.poll();
    }*/

    /**
     * get the streamer that is currently livestreaming
     * @return user that is currently livestreaming
     */
    public String getCurrentStreamer() {
        return streamers.peek();
    }

    /**
     * removes current streamer from queue after their time is up
     * called from livestream
     */
    public void pollStreamer() {
        streamers.poll();
    }



}
