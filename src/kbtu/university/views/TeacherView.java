package kbtu.university.views;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.UrgencyLevel;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;
import kbtu.university.models.users.Student;
import kbtu.university.models.users.Teacher;

import java.util.Scanner;
import java.util.List;

public class TeacherView implements ConsoleView {
    private final Teacher teacher;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalizationManager lm = LocalizationManager.getInstance();

    public TeacherView(Teacher teacher) {
        this.teacher = (Teacher) DataStorage.getInstance().getUsers().stream()
                .filter(u -> u.getId().equals(teacher.getId()))
                .findFirst()
                .orElse(null); }

    @Override
    public void render() {
        while (true) {
            System.out.println("\n--- " + lm.getString("TEACHER MENU", "ОҚЫТУШЫ МӘЗІРІ", "МЕНЮ ПРЕПОДАВАТЕЛЯ") + " ---");
            System.out.println("1. " + lm.getString("View Courses", "Курстарды көру", "Просмотр курсов"));
            System.out.println("2. " + lm.getString("Put Marks", "Баға қою", "Выставить оценки"));
            System.out.println("3. " + lm.getString("Send Complaint", "Шағым жіберу", "Отправить жалобу"));
            System.out.println("4. " + lm.getString("Send Request", "Өтініш жіберу", "Отправить запрос"));
            System.out.println("5. " + lm.getString("Logout", "Шығу", "Выйти"));
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> teacher.viewMyCourses();
                case "2" -> putMarkProcess();
                case "3" -> sendComplaintProcess();
                case "4" -> sendRequestProcess();
                case "5" -> { return; }
                default -> System.out.println(lm.getString("Invalid!", "Қате!", "Неверно!"));
            }
        }
    }

    private void sendRequestProcess() {
        System.out.print(lm.getString("Enter content: ", "Мазмұнын енгізіңіз: ", "Введите содержание: "));
        teacher.sendRequest(scanner.nextLine());
    }


    private void putMarkProcess() {
        System.out.print("Course Code: "); String code = scanner.nextLine();
        System.out.print("Student ID: "); String sId = scanner.nextLine();

        Course course = DataStorage.getInstance().getCourses().stream()
                .filter(c -> c.getCourseCode().equalsIgnoreCase(code)).findFirst().orElse(null);
        Student student = DataStorage.getInstance().getStudents().stream()
                .filter(s -> s.getId().equals(sId)).findFirst().orElse(null);

        if (course != null && student != null) {
            System.out.print("1st Att: "); double f = Double.parseDouble(scanner.nextLine());
            System.out.print("2nd Att: "); double s = Double.parseDouble(scanner.nextLine());
            System.out.print("Final Exam: "); double fin = Double.parseDouble(scanner.nextLine());
            teacher.putMark(student, course, f, s, fin);
        } else {
            System.out.println(lm.getString("Not found", "Табылмады", "Не найдено"));
        }
    }

    private void sendComplaintProcess() {
        System.out.print("Student ID: "); String sId = scanner.nextLine();
        System.out.print("Reason: "); String reason = scanner.nextLine();
        System.out.print("Urgency (LOW/MEDIUM/HIGH): "); UrgencyLevel lvl = UrgencyLevel.valueOf(scanner.nextLine().toUpperCase());

        Student student = DataStorage.getInstance().getStudents().stream()
                .filter(s -> s.getId().equals(sId)).findFirst().orElse(null);

        teacher.sendComplaint(student, reason, lvl);
    }
}