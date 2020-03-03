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
    private ConcurrentLinkedQueue<User> streamers = new ConcurrentLinkedQueue<User>();
    public ConcurrentLinkedQueue<User> getStreamers() { return streamers; }

    //TODO: override equals() and hashCode() in User.java to be able to user
    //      contains method
    //TODO: find less expensive way to do this - this won't scale well!
    /**
     * add streamer to queue
     * @param u user being added to the stream
     * @return true if streamer was successfully added, false if already in queue
     */
    public boolean addStreamer(User u) {
        Iterator i = streamers.iterator();
        String username = "";
        while (i.hasNext()) {
            User user = (User) i.next();
            username = user.getUserName();
            if (username.equals(u.getUserName())) return false;
        }
        streamers.add(u);
        return true;
    }

    /**
     * remove streamer from queue
     * @param u user being removed from stream
     * @return true if streamer was removed, false if streamer was not already in
     *          queue
     */
    public boolean removeStreamer(User u) {
        Iterator i = streamers.iterator();
        String username = "";
        while(i.hasNext()) {
            User user = (User) i.next();
            username = user.getUserName();
            if (username.equals(u.getUserName())) {
                i.remove();
                return true;
            }
        }
        return false;
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
    public User getCurrentStreamer() {
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
