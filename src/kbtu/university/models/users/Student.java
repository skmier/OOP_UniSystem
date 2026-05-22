package kbtu.university.models.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;
import kbtu.university.models.academic.StudentOrganization;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String major;
    private int yearOfStudy;
    private final Map<Course, Mark> academicJournal = new java.util.HashMap<>();
    private int failCount = 0;
    private int currentCredits = 0;
    private List<String> organizations = new ArrayList<>();
    public Student(String id, String login, String password, String name, String major, int yearOfStudy) {
        super(id, login, password, name);
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.failCount= 0;
        this.currentCredits = 0;
    }

    public void viewMyCourses() {
        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println("\n=== " + lm.getString("MY COURSES", "МЕНІҢ КУРСТАРЫМ", "МОИ КУРСЫ") + " ===");
        if (academicJournal.isEmpty()) {
            System.out.println(lm.getString("No courses.", "Курстар жоқ.", "Нет курсов."));
            return;
        }
        for (Course c : academicJournal.keySet()) {
            System.out.println(c);
        }
    }

    public void registerForCourse(Course course) {
        LocalizationManager lm = LocalizationManager.getInstance();
        if (course == null) return;

        if (this.failCount >= 3) {
            System.out.println(lm.getString(
                    "[ERROR] Registration blocked! Too many fails (" + failCount + "). Max limit: 3",
                    "[ҚАТЕ] Тіркелу бұғатталды! Қанағаттанарлықсыз бағалар саны тым көп (" + failCount + "). Шектеу: 3",
                    "[ОШИБКА] Регистрация заблокирована! Слишком много фейлов (" + failCount + "). Лимит: 3"
            ));
            return;
        }

        if (!course.getTargetMajor().equalsIgnoreCase(this.major) || course.getTargetYear() != this.yearOfStudy) {
            System.out.println(lm.getString(
                    "[ERROR] This course is intended for " + course.getTargetMajor() + " year " + course.getTargetYear(),
                    "[ҚАТЕ] Бұл курс келесі мамандыққа арналған: " + course.getTargetMajor() + ", курс: " + course.getTargetYear(),
                    "[ОШИБКА] Этот курс предназначен для " + course.getTargetMajor() + " курса " + course.getTargetYear()
            ));
            return;
        }
        if (academicJournal.containsKey(course)) {
            System.out.println(lm.getString(
                    "[ERROR] Already registered for this course!",
                    "[ҚАТЕ] Бұл курсқа тіркеліп қойғансыз!",
                    "[ОШИБКА] Уже зарегистрирован на этот курс!"
            ));
            return;
        }

        if (this.currentCredits + course.getCredits() > 21) {
            System.out.println(lm.getString(
                    "[ERROR] Credit limit exceeded! Max 21. Current: " + this.currentCredits + ", Course demands: " + course.getCredits(),
                    "[ҚАТЕ] Кредит шектеуінен асып кетті! Макс 21. Қазіргі: " + this.currentCredits + ", Курсқа керек: " + course.getCredits(),
                    "[ОШИБКА] Превышен лимит кредитов! Макс 21. Текущий: " + this.currentCredits + ", Требуется для курса: " + course.getCredits()
            ));
            return;
        }
        academicJournal.put(course, new Mark());
        this.currentCredits += course.getCredits();
        System.out.println(lm.getString(
                "[SUCCESS] Registered for course: " + course.getCourseName(),
                "[СӘТТІ] Курсқа тіркелдіңіз: " + course.getCourseName(),
                "[УСПЕШНО] Зарегистрирован на курс: " + course.getCourseName()
        ));
    }


    public void viewCourseTeacherInfo(Course course) {
        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage ds = DataStorage.getInstance();
        boolean found = false;

        for (Teacher t : ds.getTeachers()) {
            if (t.getCourses().contains(course)) {
                System.out.println(lm.getString("Teacher: ", "Оқытушы: ", "Преподаватель: ") + t.getName() + " (" + t.getPosition() + ") | Rating: " + t.getAverageRating());
                found = true;
            }
        }
        if (!found) {
            System.out.println(lm.getString("No teacher assigned yet.", "Оқытушы әлі тағайындалмаған.", "Преподаватель еще не назначен."));
        }
    }

    public void rateTeacher(Teacher teacher, double score) {
        LocalizationManager lm = LocalizationManager.getInstance();
        if (teacher == null) return;

        teacher.receiveRating(score);
        System.out.println(lm.getString("[SUCCESS] Thank you for rating!", "[СӘТТІ] Бағалағаныңыз үшін рахмет!", "[УСПЕШНО] Спасибо за оценку!"));
    }

    public void viewMarks() {
        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println("\n=== " + lm.getString("MY MARKS", "МЕНІҢ БАҒАЛАРЫМ", "МОИ ОЦЕНКИ") + " ===");
        for (Map.Entry<Course, Mark> entry : academicJournal.entrySet()) {
            System.out.println(entry.getKey().getCourseName() + ": " + entry.getValue());
        }
    }

    public double calculateGpa() {
        if (academicJournal.isEmpty()) return 0.0;
        double totalPoints = 0; int totalCredits = 0;
        for (Map.Entry<Course, Mark> entry : academicJournal.entrySet()) {
            totalPoints += entry.getValue().getGpaValue() * entry.getKey().getCredits();
            totalCredits += entry.getKey().getCredits();
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public void viewTranscript() {
        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println("\n=== " + lm.getString("TRANSCRIPT", "ТРАНСКРИПТ", "ТРАНСКРИПТ") + " ===");
        for (Map.Entry<Course, Mark> entry : academicJournal.entrySet()) {
            System.out.printf("%s | Grade: %s\n", entry.getKey().getCourseName(), entry.getValue().grade());
        }
        System.out.printf("TOTAL GPA: %.2f\n", calculateGpa());
    }

    public void joinOrganization(String orgName) {
        LocalizationManager lm = LocalizationManager.getInstance();

        List<String> myOrgs = getOrganizations();

        if (!myOrgs.contains(orgName)) {
            myOrgs.add(orgName);
            System.out.println(lm.getString(
                    "[SUCCESS] Joined organization: " + orgName,
                    "[СӘТТІ] Ұйымға қосылдыңыз: " + orgName,
                    "[УСПЕШНО] Вы вступили в организацию: " + orgName
            ));
        } else {
            System.out.println(lm.getString(
                    "[ERROR] Already a member.",
                    "[ҚАТЕ] Сіз бұл ұйымның мүшесісіз.",
                    "[ОШИБКА] Вы уже являетесь участником."
            ));
        }
    }

    public String getMajor() { return major; }
    public int getYearOfStudy() { return yearOfStudy; }
    public Map<Course, Mark> getAcademicJournal() { return academicJournal; }
    public int getFailCount() {
        return failCount;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }
    public List<String> getOrganizations() {
        if (this.organizations == null) {
            this.organizations = new ArrayList<>();
        }
        return this.organizations;
    }

    public void setCurrentCredits(int currentCredits) {
        this.currentCredits = currentCredits;
    }
}