package com.limelight.server;
import java.util.concurrent.*;

public class StreamQueue {
    //usage: StreamQueue queue = StreamQueue.getInstance();
    private static StreamQueue sq = new StreamQueue();
    public static StreamQueue getInstance() { return sq; }

    //usage: queue.getStreamers();
    private ConcurrentLinkedQueue<User> streamers;
    public ConcurrentLinkedQueue<User> getStreamers() { return streamers; }

    public boolean addStreamer(User u) {
        //@TODO: find a less expensive way to do this - this won't scale well
        if (streamers.contains(u)) return false;
        streamers.add(u);
        return true;
    }
    public boolean removeStreamer(User u) {
        return streamers.remove(u);
    }

    public User nextStreamer() {
        return streamers.poll();
    }

}
