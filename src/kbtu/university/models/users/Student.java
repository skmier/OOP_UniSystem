package kbtu.university.models.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kbtu.university.core.DataStorage;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;
import kbtu.university.models.academic.StudentOrganization;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String major;
    private int yearOfStudy;
    private int totalCredits;
    private Map<Course, Mark> academicJournal;
    private List<StudentOrganization> organizations = new ArrayList<>();

    public Student(String id, String login, String password, String name, String major, int yearOfStudy){
        super(id, login, password, name);
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.totalCredits = 0;
        this.academicJournal = new HashMap<>();
    }

    public void registerForCourse(Course course){
        if(!academicJournal.containsKey(course)){
            academicJournal.put(course,new Mark());
            System.out.println("[SUCCESS] Student registered for a course");
        }else {
            System.out.println("[ERROR] You`ve already registered");
        }
    }


    public void joinOrganization(StudentOrganization organization) {
        if (organization == null) {
            System.out.println("[ERROR] Organization does not exist.");
            return;
        }

        if (!this.organizations.contains(organization)) {
            this.organizations.add(organization);
            organization.addMember(this);

            DataStorage.getInstance().getActionLogs().add(
                    new UserActionLog(this.getId(), "JOIN_ORGANIZATION", "Joined club: " + organization.getName())
            );
            System.out.println("[SUCCESS] You successfully joined " + organization.getName());
        } else {
            System.out.println("[WARNING] You are already a member of " + organization.getName());
        }
    }

    public double calculateTotalGpa(){
        if (academicJournal.isEmpty()) return 0.0;

        double totalWeightedGpa = 0.0;
        int creditsAttempted = 0;

        for (Map.Entry<Course, Mark> entry : academicJournal.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();

            totalWeightedGpa += mark.getGpaValue() * course.getCredits();
            creditsAttempted += course.getCredits();
        }

        if (creditsAttempted == 0) return 0.0;
        return totalWeightedGpa / creditsAttempted;
    }

    public void rateTeacher(Teacher teacher, int score) {
        if (teacher == null) {
            System.out.println("[ERROR] Teacher not found.");
            return;
        }
        if (score < 1 || score > 10) {
            System.out.println("[ERROR] Rating score must be between 1 and 10.");
            return;
        }

        System.out.println("[SUCCESS] You rated teacher " + teacher.getName() + " with score: " + score);

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "RATE_TEACHER", "Rated " + teacher.getName() + " with " + score)
        );
    }

    public void viewTranscript(){
        System.out.println("\n=============================================");
        System.out.println("                Transcript                   ");
        System.out.println("=============================================");

        if (academicJournal.isEmpty()) {
            System.out.println("You have`nt registered any course");
            System.out.println("=============================================");
            return;
        }

        for (Map.Entry<Course, Mark> entry : academicJournal.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();
            System.out.printf("%s (%d credits) -> %s\n",
                    course.getName(), course.getCredits(), mark.toString());
        }

        System.out.println("---------------------------------------------");
        System.out.println("Total GPA %.2f\n" + calculateTotalGpa());
        System.out.println("=============================================");
    }

    public void viewOrganizations() {
        System.out.println("\n=============================================");
        System.out.println("       Organizations for " + getName());
        System.out.println("=============================================");
        if (organizations.isEmpty()) {
            System.out.println("Not a member of any organization yet.");
        } else {
            for (StudentOrganization org : organizations) {
                String role = org.getHead().equals(this) ? "HEAD" : "MEMBER";
                System.out.println("- " + org.getName() + " [Role: " + role + "]");
            }
        }
        System.out.println("=============================================");
    }

    public String getMajor() {
        return major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    @Override
    public String toString() {
        return "Student: " + getName() + " Major " + major + " Year of Study " + yearOfStudy + " GPA: " + calculateTotalGpa();
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Map<Course, Mark> getAcademicJournal() {
        return academicJournal;
    }
}
