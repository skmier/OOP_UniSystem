package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.CourseType;
import kbtu.university.enums.ManagerType;
import kbtu.university.models.academic.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String id, String login, String password, String name, ManagerType managerType) {
        super(id, login, password, name);
        this.managerType = managerType;
    }

    public void createCourseForRegistration(String code, String name, int credits, CourseType type, String major, int year) {
        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage ds = DataStorage.getInstance();

        for (Course c : ds.getCourses()) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                System.out.println(lm.getString(
                        "[ERROR] Course with code " + code + " already exists!",
                        "[ҚАТЕ] " + code + " коды бар курс қазірдің өзінде бар!",
                        "[ОШИБКА] Курс с кодом " + code + " уже существует!"
                ));
                return;
            }
        }

        Course newCourse = new Course(code, name, credits, type, major, year);
        ds.getCourses().add(newCourse);

        ds.getActionLogs().add(new UserActionLog(this.getId(), "CREATE_COURSE", "Course: " + name));

        System.out.println(lm.getString(
                "[SUCCESS] Course added for registration: " + name,
                "[СӘТТІ] Курс тіркелуге сәтті қосылды: " + name,
                "[УСПЕШНО] Курс добавлен для регистрации: " + name
        ));
    }

    public void addNews(String title, String content, boolean isResearch) {
        LocalizationManager lm = LocalizationManager.getInstance();

        DataStorage.getInstance().getUniversityNews().add(new News(title, content));

        DataStorage.getInstance().getActionLogs().add(new UserActionLog(this.getId(), "ADD_NEWS", "Title: " + title));

        System.out.println(lm.getString(
                "[SUCCESS] News published successfully.",
                "[СӘТТІ] Жаңалық сәтті жарияланды.",
                "[УСПЕШНО] Новость успешно опубликована."
        ));
    }

    public void assignCourseToTeacher(Teacher teacher, Course course) {
        LocalizationManager lm = LocalizationManager.getInstance();

        if (teacher == null || course == null) {
            System.out.println(lm.getString("[ERROR] Invalid data!", "[ҚАТЕ] Мәліметтер қате!", "[ОШИБКА] Неверные данные!"));
            return;
        }

        teacher.assignCourse(course);

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "ASSIGN_COURSE", "To: " + teacher.getName() + " | Course: " + course.getCourseName())
        );
        DataStorage.getInstance().save();
        System.out.println(lm.getString(
                "[SUCCESS] Course assigned to " + teacher.getName(),
                "[СӘТТІ] Курс " + teacher.getName() + " оқытушысына тағайындалды",
                "[УСПЕШНО] Курс назначен преподавателю " + teacher.getName()
        ));
    }

    public void approveStudentRegistration(Student student, Course course) {
        LocalizationManager lm = LocalizationManager.getInstance();

        if (student == null || course == null) {
            System.out.println(lm.getString("[ERROR] Invalid data!", "[ҚАТЕ] Мәліметтер қате!", "[ОШИБКА] Неверные данные!"));
            return;
        }
        student.getAcademicJournal().put(course, new Mark());
        DataStorage.getInstance().save();

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "APPROVE_REGISTRATION", "Student: " + student.getName() + " | Course: " + course.getCourseName())
        );

        System.out.println(lm.getString(
                "[SUCCESS] Registration APPROVED for " + student.getName(),
                "[СӘТТІ] " + student.getName() + " студентінің тіркелуі ҚАБЫЛДАНДЫ",
                "[УСПЕШНО] Регистрация ОДОБРЕНА для " + student.getName()
        ));
    }

    public void viewStudentsSorted(boolean byGpa) {
        LocalizationManager lm = LocalizationManager.getInstance();
        List<Student> students = new ArrayList<>(DataStorage.getInstance().getStudents());

        if (students.isEmpty()) {
            System.out.println(lm.getString("No students found.", "Студенттер табылдады.", "Студентов не найдено."));
            return;
        }

        if (byGpa) {
            students.sort((s1, s2) -> Double.compare(s2.calculateGpa(), s1.calculateGpa()));
            System.out.println("\n--- " + lm.getString("STUDENTS SORTED BY GPA", "GPA БОЙЫНША СҰРЫПТАЛҒАН СТУДЕНТТЕР", "СТУДЕНТЫ ОТСОРТИРОВАННЫЕ ПО GPA") + " ---");
        } else {
            students.sort(Comparator.comparing(User::getName));
            System.out.println("\n--- " + lm.getString("STUDENTS SORTED ALPHABETICALLY", "ӘЛІПБИ БОЙЫНША СҰРЫПТАЛҒАН СТУДЕНТТЕР", "СТУДЕНТЫ ОТСОРТИРОВАННЫЕ ПО АЛФАВИТУ") + " ---");
        }

        for (Student s : students) {
            System.out.printf("%s | %s: %s | GPA: %.2f\n",
                    s.getName(),
                    lm.getString("Major", "Мамандық", "Специальность"),
                    s.getMajor(),
                    s.calculateGpa()
            );
        }
    }

    public void viewTeachersSorted() {
        LocalizationManager lm = LocalizationManager.getInstance();
        List<Teacher> teachers = new ArrayList<>(DataStorage.getInstance().getTeachers());

        if (teachers.isEmpty()) {
            System.out.println(lm.getString("No teachers found.", "Оқытушылар табылмады.", "Преподавателей не найдено."));
            return;
        }

        teachers.sort(Comparator.comparing(User::getName));

        System.out.println("\n--- " + lm.getString("TEACHERS SORTED ALPHABETICALLY", "ӘЛІПБИ БОЙЫНША СҰРЫПТАЛҒАН ОҚЫТУШЫЛАР", "ПРЕПОДАВАТЕЛИ ОТСОРТИРОВАННЫЕ ПО АЛФАВИТУ") + " ---");
        for (Teacher t : teachers) {
            System.out.printf("%s | %s: %s | Rating: %.2f\n",
                    t.getName(),
                    lm.getString("Position", "Лауазымы", "Должность"),
                    t.getPosition(),
                    t.getAverageRating()
            );
        }
    }


    public void generateAcademicReport() {
        LocalizationManager lm = LocalizationManager.getInstance();
        List<Student> students = DataStorage.getInstance().getStudents();

        System.out.println("\n--- " + lm.getString("ACADEMIC PERFORMANCE REPORT", "АКАДЕМИЯЛЫҚ КӨРСЕТКІШТЕР ЕСЕБІ", "ОТЧЕТ ОБ УСПЕВАЕМОСТИ") + " ---");
        double totalGpa = 0;
        for (Student s : students) {
            System.out.println(s.getName() + " | GPA: " + String.format("%.2f", s.calculateGpa()));
            totalGpa += s.calculateGpa();
        }
        double avg = totalGpa / students.size();
        System.out.println(lm.getString("University Average GPA: ", "Университеттің орташа GPA-і: ", "Средний GPA университета: ") + String.format("%.2f", avg));
    }
    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    public void viewRequests() {
        LocalizationManager lm = LocalizationManager.getInstance();
        List<Request> requests = DataStorage.getInstance().getRequests();

        System.out.println("\n--- " + lm.getString("EMPLOYEE REQUESTS", "ҚЫЗМЕТКЕРЛЕРДІҢ ӨТІНІШТЕРІ", "ЗАПРОСЫ СОТРУДНИКОВ") + " ---");

        if (requests == null || requests.isEmpty()) {
            System.out.println(lm.getString("No pending requests.", "Күтудегі өтініштер жоқ.", "Нет активных запросов."));
        } else {
            for (int i = 0; i < requests.size(); i++) {
                System.out.println((i + 1) + ". " + requests.get(i).toString());
            }
        }
    }
}