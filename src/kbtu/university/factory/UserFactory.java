package kbtu.university.factory;

import kbtu.university.enums.ManagerType;
import kbtu.university.enums.TeacherPosition;
import kbtu.university.enums.UserType;
import kbtu.university.models.users.*;

public class UserFactory {
    public static User createUser(UserType type, String id, String login, String password, String name, String extra) {
        return switch (type) {
            case STUDENT -> new Student(id, login, password, name, extra.isEmpty() ? "General" : extra, 1);

            case TEACHER -> {
                TeacherPosition pos;
                try { pos = TeacherPosition.valueOf(extra.toUpperCase()); }
                catch (Exception e) { pos = TeacherPosition.PROFESSOR; }
                yield new Teacher(id, login, password, name, pos);
            }

            case MANAGER -> {
                ManagerType mType;
                try { mType = ManagerType.valueOf(extra.toUpperCase()); }
                catch (Exception e) { mType = ManagerType.OR; }
                yield new Manager(id, login, password, name, mType);
            }

            case ADMIN -> new Admin(id, login, password, name);
            case TECH_SUPPORT -> new TechSupportSpecialist(id, login, password, name);
            case RESEARCHER -> new GraduateStudent(id, login, password, name, extra.isEmpty() ? "General" : extra, 1);
        };
    }
}
