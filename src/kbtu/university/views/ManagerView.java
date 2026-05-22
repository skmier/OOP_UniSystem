package kbtu.university.views;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.CourseType;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.News;
import kbtu.university.models.academic.Request;
import kbtu.university.models.users.Manager;
import kbtu.university.models.users.Student;
import kbtu.university.models.users.Teacher;

import java.util.List;
import java.util.Scanner;

public class ManagerView implements ConsoleView {
    private final Manager manager;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalizationManager lm = LocalizationManager.getInstance();

    public ManagerView(Manager manager) { this.manager = manager; }

    @Override
    public void render() {
        while (true) {
            System.out.println("\n--- " + lm.getString("MANAGER MENU", "МЕНЕДЖЕР МӘЗІРІ", "МЕНЮ МЕНЕДЖЕРА") + " (" + manager.getManagerType() + ") ---");
            System.out.println("1. " + lm.getString("Add Course", "Курс қосу", "Добавить курс"));
            System.out.println("2. " + lm.getString("Assign Teacher", "Мұғалім тағайындау", "Назначить преподавателя"));
            System.out.println("3. " + lm.getString("Approve Reg", "Тіркеуді мақұлдау", "Одобрить регистрацию"));
            System.out.println("4. " + lm.getString("Add News", "Жаңалық қосу", "Добавить новость"));
            System.out.println("5. " + lm.getString("Sort Info", "Сұрыптау", "Сортировка (Студенты/Учителя)"));
            System.out.println("6. " + lm.getString("Academic Report", "Академиялық есеп", "Академический отчет"));
            System.out.println("7. " + lm.getString("View Requests", "Өтініштерді қарау", "Просмотр запросов"));
            System.out.println("8. " + lm.getString("Send Request", "Өтініш жіберу", "Отправить запрос"));
            System.out.println("9. " + lm.getString("Logout", "Шығу", "Выйти"));

            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addCourse();
                case "2" -> assignTeacher();
                case "3" -> approveReg();
                case "4" -> addNews();
                case "5" -> sortMenu();
                case "6" -> manager.generateAcademicReport();
                case "7" -> manager.viewRequests();
                case "8" -> sendRequestProcess();
                case "9" -> { return; }
                default -> System.out.println(lm.getString("Invalid choice!", "Қате таңдау!", "Неверный выбор!"));
            }
        }
    }
    private void sendRequestProcess() {
        System.out.print(lm.getString("Enter content: ", "Мазмұнын енгізіңіз: ", "Введите содержание: "));
        manager.sendRequest(scanner.nextLine());
    }

    private void addCourse() {
        System.out.print("Code: "); String code = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Credits: "); int cred = Integer.parseInt(scanner.nextLine());
        System.out.print("Major: "); String major = scanner.nextLine();
        System.out.print("Year: "); int year = Integer.parseInt(scanner.nextLine());
        manager.createCourseForRegistration(code, name, cred, CourseType.FREE_ELECTIVE, major, year);
    }

    private void assignTeacher() {
        System.out.print("Enter Teacher Name: "); String tName = scanner.nextLine();
        System.out.print("Enter Course Name: "); String cName = scanner.nextLine();

        Teacher t = DataStorage.getInstance().getTeachers().stream()
                .filter(x -> x.getName().equalsIgnoreCase(tName)).findFirst().orElse(null);
        Course c = DataStorage.getInstance().getCourses().stream()
                .filter(x -> x.getCourseName().equalsIgnoreCase(cName)).findFirst().orElse(null);

        manager.assignCourseToTeacher(t, c);
    }

    private void sortMenu() {
        System.out.println("1. Students (GPA) | 2. Students (Alpha) | 3. Teachers (Alpha)");
        String sub = scanner.nextLine();
        switch(sub) {
            case "1" -> manager.viewStudentsSorted(true);
            case "2" -> manager.viewStudentsSorted(false);
            case "3" -> manager.viewTeachersSorted();
        }
    }

    private void addNews() {
        System.out.print("Title: "); String t = scanner.nextLine();
        System.out.print("Content: "); String c = scanner.nextLine();
        manager.addNews(t, c, false);
    }

    private void approveReg() {
        System.out.print("Student Name: "); String sName = scanner.nextLine();
        System.out.print("Course Name: "); String cName = scanner.nextLine();
        Student s = DataStorage.getInstance().getStudents().stream().filter(x -> x.getName().equalsIgnoreCase(sName)).findFirst().orElse(null);
        Course c = DataStorage.getInstance().getCourses().stream().filter(x -> x.getCourseName().equalsIgnoreCase(cName)).findFirst().orElse(null);
        manager.approveStudentRegistration(s, c);
    }


}