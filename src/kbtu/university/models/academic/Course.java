package kbtu.university.models.academic;

import kbtu.university.models.users.Student;
import kbtu.university.models.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private int credits;
    private int capacity;

    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> instructors = new ArrayList<>();

    private String targetMajor;
    private int targetYear;
    private boolean isRegistrationOpen;

    public Course(String code, String name, int credits) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.capacity = 70;
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public boolean addStudent(Student student) {
        if (students.size() >= capacity) {
            System.out.println("[ERROR] Course " + name + " is full! Capacity limit reached.");
            return false;
        }
        if (!students.contains(student)) {
            students.add(student);
            return true;
        }
        return false;
    }

    public void addInstructor(Teacher teacher) {
        if (teacher == null) return;
        if (!instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    public List<Teacher> getInstructors() { return instructors; }
    public List<Student> getStudents() { return students; }
    public int getCapacity() { return capacity; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getTargetMajor() { return targetMajor; }
    public void setTargetMajor(String targetMajor) { this.targetMajor = targetMajor; }
    public int getTargetYear() { return targetYear; }
    public void setTargetYear(int targetYear) { this.targetYear = targetYear; }
    public boolean isRegistrationOpen() { return isRegistrationOpen; }
    public void setRegistrationOpen(boolean registrationOpen) { this.isRegistrationOpen = registrationOpen; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() { return Objects.hash(code); }

    @Override
    public String toString() {
        return "Course{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", credits=" + credits + '}';
    }
}