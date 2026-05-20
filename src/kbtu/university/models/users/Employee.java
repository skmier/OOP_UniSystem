package kbtu.university.models.users;

public abstract class Employee extends User{
    private static final long serialVersionUID = 1L;

    public Employee(String id, String login, String password, String name) {
        super(id, login, password, name);
    }
}
