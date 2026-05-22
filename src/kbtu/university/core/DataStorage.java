package kbtu.university.core;

import kbtu.university.models.academic.Complaint;
import kbtu.university.models.academic.Course;
import kbtu.university.models.academic.News;
import kbtu.university.models.academic.Request;
import kbtu.university.models.users.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static DataStorage instance;
    private static final String FILE_PATH = "university_data.ser";


    private  List<User> users = new ArrayList<>();
    private  List<UserActionLog> actionLogs = new ArrayList<>();
    private  List<Course> courses = new ArrayList<>();
    private  List<Complaint> complaints = new ArrayList<>();
    private  List<News> universityNews = new ArrayList<>();
    private List<Request> requests = new ArrayList<>(); // Инициализация здесь

    public List<Request> getRequests() {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        return requests;
    }

    public List<News> getUniversityNews() {
        return universityNews;
    }

    private DataStorage(){};

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("[SYSTEM ERROR] File not Found");
        } catch (IOException e) {
            System.out.println("[SYSTEM ERROR] Error with saving data: " + e.getMessage());
        }
    }
    private static DataStorage load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new DataStorage();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (DataStorage) ois.readObject();
        } catch (Exception e) {
            System.out.println("[SYSTEM WARNING] Load error, new DB is created. Reason: " + e.getMessage());
            return new DataStorage();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserActionLog> getActionLogs() {
        return actionLogs;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Student) {
                students.add((Student) u);
            }
        }
        return students;
    }

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Teacher) {
                teachers.add((Teacher) u);
            }
        }
        return teachers;
    }
    public boolean hasAdmin() {
        return users.stream().anyMatch(u -> u instanceof Admin);
    }
}
