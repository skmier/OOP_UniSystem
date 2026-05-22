package kbtu.university.models.academic;

import kbtu.university.enums.RequestStatus;
import kbtu.university.enums.UrgencyLevel;
import kbtu.university.models.users.Student;
import kbtu.university.models.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Teacher sender;
    private Student studentAbout;
    private String text;
    private UrgencyLevel urgencyLevel;
    private Date timestamp;
    private RequestStatus status;


    public Complaint(Teacher sender, Student studentAbout, String text, UrgencyLevel urgencyLevel) {
        this.sender = sender;
        this.studentAbout = studentAbout;
        this.text = text;
        this.urgencyLevel = urgencyLevel;
        this.timestamp = new Date();
        this.status = RequestStatus.VIEWED;
    }

    public Teacher getSender() { return sender; }
    public Student getStudentAbout() { return studentAbout; }
    public String getText() { return text; }
    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public Date getTimestamp() { return timestamp;}
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "[" + urgencyLevel + "] Complaint by " + sender.getName() + " on " + studentAbout.getName() + ": " + text;
    }
}
