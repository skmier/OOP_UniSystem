package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.enums.TeacherPosition;
import kbtu.university.enums.UrgencyLevel;
import kbtu.university.models.academic.Complaint;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherPosition position;
    private List<Course> courses = new ArrayList<>();

    public Teacher(String id, String login, String password, String name, TeacherPosition position) {
        super(id, login, password, name);
        this.position = position;
    }

    public void sendComplaint(Manager manager, String studentName, String complaintText, UrgencyLevel urgency) {
        if (manager == null || studentName == null || complaintText == null) {
            System.out.println("[ERROR] Invalid complaint data or manager not found.");
            return;
        }

        Complaint complaint = new Complaint(this.getName(), studentName, complaintText, urgency);
        manager.getComplaintsInbox().add(complaint);

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "SEND_COMPLAINT", "To Manager: " + manager.getName() + " | Level: " + urgency)
        );

        System.out.println("[SUCCESS] Complaint sent to manager " + manager.getName() + " with urgency level: " + urgency);
    }

    public void assignMark(Student student, Course course, double firstAtt, double secondAtt, double finalExam) {
        if (student.getAcademicJournal().containsKey(course)) {
            Mark mark = student.getAcademicJournal().get(course);

            if (firstAtt >= 0) mark.setFirstAtt(firstAtt);
            if (secondAtt >= 0) mark.setSecondAtt(secondAtt);
            if (finalExam >= 0) mark.setFinalExam(finalExam);

            System.out.println("[SUCCESS] Marks updated for student " + student.getName() + " on course " + course.getName());

            DataStorage.getInstance().getActionLogs().add(
                    new UserActionLog(this.getId(), "ASSIGN_MARK", "Updated marks for " + student.getName())
            );
            DataStorage.getInstance().save();
        } else {
            System.out.println("[ERROR] Student is not registered for this course!");
        }
    }

    public List<Course> getCourses() {
        if (this.courses == null) {
            this.courses = new ArrayList<>();
        }
        return this.courses;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Teacher: " + getName() + " | Position: " + getPosition();
    }
}