package kbtu.university.models.academic;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String senderId;
    private final String senderName;
    private final String content;
    private final Date timestamp;
    private boolean isRead;

    public Message(String senderId, String senderName, String content) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.timestamp = new Date();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public String getSenderId() { return senderId; }
    public String getSenderName() { return senderName; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}
