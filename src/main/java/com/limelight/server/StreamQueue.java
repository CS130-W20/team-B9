package com.limelight.server;
import java.util.concurrent.*;

public class StreamQueue {
    //usage: StreamQueue queue = StreamQueue.getInstance();
    private static StreamQueue sq = new StreamQueue();
    public static StreamQueue getInstance() { return sq; }

    //usage: queue.getStreamers();
    private ConcurrentLinkedQueue<User> streamers;
    public ConcurrentLinkedQueue<User> getStreamers() { return users; }

    public boolean addStreamer(User u) {
        //@TODO: find a lesser expensive way to do this - this won't scale well
        if (users.contains(u)) return false;
        users.add(u);
        return true;
    }
    public boolean removeStreamer(User u) {
        return users.remove(u);
    }

    public User nextStreamer() {
        return users.poll();
    }


}
