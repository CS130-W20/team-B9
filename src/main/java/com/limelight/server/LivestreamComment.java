package com.limelight.server;

public class LivestreamComment {
    private String username;

    private String comment;
    
    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LivestreamComment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }
}
