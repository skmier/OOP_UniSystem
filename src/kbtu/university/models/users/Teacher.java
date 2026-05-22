package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.TeacherPosition;
import kbtu.university.enums.UrgencyLevel;
import kbtu.university.models.academic.Complaint;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.Mark;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherPosition position;
    private final List<Course> courses = new ArrayList<>();
    private double totalRatingPoints = 0;
    private int ratingCount = 0;

    public Teacher(String id, String login, String password, String name, TeacherPosition position) {
        super(id, login, password, name);
        this.position = position;
    }

    public void receiveRating(double score) {
        if (score >= 1 && score <= 5) {
            this.totalRatingPoints += score;
            this.ratingCount++;
        }
    }

    public double getAverageRating() {
        return ratingCount == 0 ? 0.0 : totalRatingPoints / ratingCount;
    }

    public void assignCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    public void putMark(Student student, Course course, double firstAtt, double secondAtt, double finalEx) {
        LocalizationManager lm = LocalizationManager.getInstance();

        if (student == null || course == null) {
            System.out.println(lm.getString("[ERROR] Invalid data", "[ҚАТЕ] Мәліметтер қате", "[ОШИБКА] Неверные данные"));
            return;
        }

        if (!courses.contains(course)) {
            System.out.println(lm.getString(
                    "[ERROR] You do not teach this course!",
                    "[ҚАТЕ] Сіз бұл курсты жүргізбейсіз!",
                    "[ОШИБКА] Вы не ведете этот курс!"
            ));
            return;
        }

        if (!student.getAcademicJournal().containsKey(course)) {
            System.out.println(lm.getString(
                    "[ERROR] Student is not registered for this course!",
                    "[ҚАТЕ] Студент бұл курсқа тіркелмеген!",
                    "[ОШИБКА] Студент не зарегистрирован на этот курс!"
            ));
            return;
        }

        Mark mark = student.getAcademicJournal().get(course);
        mark.setFirstAtt(firstAtt);
        mark.setSecondAtt(secondAtt);
        mark.setFinalExam(finalEx);

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "PUT_MARK", "Student: " + student.getName() + " | Course: " + course.getCourseName())
        );

        System.out.println(lm.getString(
                "[SUCCESS] Mark updated for student: " + student.getName(),
                "[СӘТТІ] Студенттің бағасы сәтті өзгертілді: " + student.getName(),
                "[УСПЕШНО] Оценка обновлена для студента: " + student.getName()
        ));
    }


    public void sendComplaint(Student student, String reason, UrgencyLevel urgency) {
        LocalizationManager lm = LocalizationManager.getInstance();

        if (student == null || reason == null || reason.trim().isEmpty()) {
            System.out.println(lm.getString("[ERROR] Invalid complaint field", "[ҚАТЕ] Шағым өрісі қате", "[ОШИБКА] Неверные поля жалобы"));
            return;
        }

        Complaint complaint = new Complaint(this, student, reason, urgency);

        DataStorage.getInstance().getComplaints().add(complaint);
        DataStorage.getInstance().save();

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "SEND_COMPLAINT", "Against student: " + student.getName() + " | Level: " + urgency)
        );

        System.out.println(lm.getString(
                "[SUCCESS] Complaint submitted with " + urgency + " urgency.",
                "[СӘТТІ] Шағым сәтті жіберілді, маңыздылығы: " + urgency,
                "[УСПЕШНО] Жалоба отправлена с уровнем срочности: " + urgency
        ));
    }

    public void viewMyCourses() {
        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println("\n=== " + lm.getString("MY COURSES", "МЕНІҢ КУРСТАРЫМ", "МОИ КУРСЫ") + " ===");
        if (courses.isEmpty()) {
            System.out.println(lm.getString("No assigned courses.", "Курстар тағайындалмаған.", "Нет назначенных курсов."));
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Teacher: " + getName() + " | Position: " + getPosition();
    }
}