package kbtu.university.models.users;

import kbtu.university.enums.TeacherPosition;

public class Teacher extends Employee{
    private static final long serialVersionUID = 1L;

    private TeacherPosition position;

    public Teacher(String id, String login, String password, String name, TeacherPosition position) {
        super(id, login, password, name);
        this.position = position;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Teacher: "  + getName() + " position: " + getPosition();
    }
}
