package kbtu.university.models.academic;

import kbtu.university.models.users.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Student head;
    private final List<Student> members = new ArrayList<>();

    public StudentOrganization(String name, Student head) {
        this.name = name;
        this.head = head;
        this.members.add(head);
    }

    public void addMember(Student student) {
        if (!members.contains(student)) {
            members.add(student);
            System.out.println("[SUCCESS] " + student.getName() + " joined " + this.name);
        } else {
            System.out.println("[WARNING] Student is already a member of " + this.name);
        }
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Student getHead() { return head; }
    public void setHead(Student head) { this.head = head; }
    public List<Student> getMembers() { return members; }
}

