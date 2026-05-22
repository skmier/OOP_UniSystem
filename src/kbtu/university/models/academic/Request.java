package kbtu.university.models.academic;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senderId;
    private String content;
    private boolean isSignedByDean;
    private boolean isSignedByRector;

    public Request(String senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.isSignedByDean = false;
        this.isSignedByRector = false;
    }
    public void signByDean() { this.isSignedByDean = true; }
    public void signByRector() { this.isSignedByRector = true; }

    public String getSenderId() { return senderId; }
    public String getContent() { return content; }
    public boolean isSignedByDean() { return isSignedByDean; }
    public boolean isSignedByRector() { return isSignedByRector; }

    @Override
    public String toString() {
        return String.format("Request from %s: %s | [Dean: %s] [Rector: %s]",
                senderId,
                content,
                isSignedByDean ? "SIGNED" : "PENDING",
                isSignedByRector ? "SIGNED" : "PENDING");
    }
}