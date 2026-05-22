package kbtu.university.models.academic;

import kbtu.university.enums.UrgencyLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String senderName;
    private final String studentName;
    private final String details;
    private final UrgencyLevel urgency;
    private final Date timestamp;
    private boolean isResolved;

    public Complaint(String senderName, String studentName, String details, UrgencyLevel urgency) {
        this.senderName = senderName;
        this.studentName = studentName;
        this.details = details;
        this.urgency = urgency;
        this.timestamp = new Date();
        this.isResolved = false;
    }

    public void resolve() {
        this.isResolved = true;
    }

    @Override
    public String toString() {
        String status = isResolved ? "[RESOLVED]" : "[PENDING]";
        return String.format("%s [%s] Urgency: %s | From Teacher: %s | On Student: %s -> Details: %s",
                status, timestamp, urgency, senderName, studentName, details);
    }

    public String getSenderName() { return senderName; }
    public String getStudentName() { return studentName; }
    public String getDetails() { return details; }
    public UrgencyLevel getUrgency() { return urgency; }
    public boolean isResolved() { return isResolved; }
}
