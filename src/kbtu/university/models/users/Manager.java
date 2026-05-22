package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.enums.ManagerType;
import kbtu.university.models.academic.Complaint;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;
    private final List<Complaint> complaintsInbox = new ArrayList<>();

    public Manager(String id, String login, String password, String name, ManagerType managerType) {
        super(id, login, password, name);
        this.managerType = managerType;
    }

    public void addCourseForRegistration(Course course) {
        if (course == null) return;

        course.setRegistrationOpen(true);
        System.out.println("[SUCCESS] Manager " + getName() + " opened course for registration: " + course.getName());

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "OPEN_COURSE", "Opened registration for course: " + course.getCode())
        );
        DataStorage.getInstance().save();
    }

    public void approveRegistration(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println("[ERROR] Invalid student or course data.");
            return;
        }

        boolean hasSpace = course.addStudent(student);

        if (hasSpace) {
            if (student.getAcademicJournal() != null && !student.getAcademicJournal().containsKey(course)) {
                student.getAcademicJournal().put(course, new Mark());
            }
            System.out.println("[SUCCESS] Registration APPROVED by manager " + getName() + " for student " + student.getName() + " to course " + course.getCode());

            DataStorage.getInstance().getActionLogs().add(
                    new UserActionLog(this.getId(), "APPROVE_REGISTRATION", "Approved student ID: " + student.getId() + " for course: " + course.getCode())
            );
            DataStorage.getInstance().save();
        } else {
            System.out.println("[ERROR] Cannot approve registration. Course is full or student already there.");
        }
    }

    public List<Complaint> getComplaintsInbox() {
        return complaintsInbox;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }
}
