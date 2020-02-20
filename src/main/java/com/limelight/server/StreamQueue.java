package com.limelight.server;
import java.util.concurrent.*;
import java.util.*;

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

    public User nextStreamer() {
        return streamers.poll();
    }

}
