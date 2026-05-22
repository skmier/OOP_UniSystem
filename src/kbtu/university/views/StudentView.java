package kbtu.university.views;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.Language;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;
import kbtu.university.models.users.Student;
import kbtu.university.models.users.Teacher;

import java.util.List;
import java.util.Scanner;

    public class StudentView implements ConsoleView {
        private final Student student;
        private final Scanner scanner = new Scanner(System.in);
        private final LocalizationManager lm = LocalizationManager.getInstance();

        public StudentView(Student student) { this.student = student; }

        @Override
        public void render() {
            while (true) {
                System.out.println("\n--- " + lm.getString("STUDENT MENU", "СТУДЕНТ МӘЗІРІ", "МЕНЮ СТУДЕНТА") + " ---");
                System.out.println("1. " + lm.getString("View My Courses", "Курстарымды көру", "Мои курсы"));
                System.out.println("2. " + lm.getString("Register Course", "Курсқа тіркелу", "Регистрация на курс"));
                System.out.println("3. " + lm.getString("View Marks", "Бағаларды көру", "Мои оценки"));
                System.out.println("4. " + lm.getString("View Transcript", "Транскрипт", "Транскрипт"));
                System.out.println("5. " + lm.getString("Rate Teacher", "Оқытушыны бағалау", "Оценить преподавателя"));
                System.out.println("6. " + lm.getString("Join Organization", "Ұйымға кіру", "Вступить в организацию"));
                System.out.println("7. " + lm.getString("Logout", "Шығу", "Выйти"));
                System.out.print("> ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> student.viewMyCourses();
                    case "2" -> registerProcess();
                    case "3" -> student.viewMarks();
                    case "4" -> student.viewTranscript();
                    case "5" -> rateTeacherProcess();
                    case "6" -> joinOrgProcess();
                    case "7" -> { return; }
                    default -> System.out.println(lm.getString("Invalid choice!", "Қате таңдау!", "Неверный выбор!"));
                }
            }
        }
        private void joinOrgProcess() {
            System.out.print(lm.getString("Enter organization name: ", "Ұйымның атын енгізіңіз: ", "Введите название организации: "));
            String org = scanner.nextLine();
            student.joinOrganization(org);
        }

        private void rateTeacherProcess() {
            System.out.print(lm.getString("Enter Teacher Name: ", "Оқытушының атын енгізіңіз: ", "Введите имя преподавателя: "));
            String name = scanner.nextLine();

            Teacher t = DataStorage.getInstance().getTeachers().stream()
                    .filter(teacher -> teacher.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);

            if (t != null) {
                System.out.print(lm.getString("Enter score (1-5): ", "Бағаны енгізіңіз (1-5): ", "Введите оценку (1-5): "));
                try {
                    double score = Double.parseDouble(scanner.nextLine());
                    student.rateTeacher(t, score);
                } catch (NumberFormatException e) {
                    System.out.println(lm.getString("Invalid number!", "Қате сан!", "Неверный формат числа!"));
                }
            } else {
                System.out.println(lm.getString("Teacher not found!", "Оқытушы табылмады!", "Преподаватель не найден!"));
            }
        }

        private void registerProcess() {
            List<Course> allCourses = DataStorage.getInstance().getCourses();
            System.out.println(lm.getString("Available Courses:", "Қолжетімді курстар:", "Доступные курсы:"));
            for (int i = 0; i < allCourses.size(); i++) {
                System.out.println((i + 1) + ". " + allCourses.get(i).getCourseName());
            }

            System.out.print(lm.getString("Enter number: ", "Нөмірді енгізіңіз: ", "Введите номер: "));
            int idx = Integer.parseInt(scanner.nextLine()) - 1;

            if (idx >= 0 && idx < allCourses.size()) {
                student.registerForCourse(allCourses.get(idx));
            } else {
                System.out.println(lm.getString("Invalid index", "Қате индекс", "Неверный индекс"));
            }
        }
    }