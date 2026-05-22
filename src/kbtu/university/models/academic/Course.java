package kbtu.university.models.academic;

import kbtu.university.enums.CourseType;
import java.io.Serializable;


public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseName;
    private int credits;
    private CourseType courseType;
    private String targetMajor;
    private int targetYear;

    public Course(String courseCode, String courseName, int credits, CourseType courseType, String targetMajor, int targetYear) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.courseType = courseType;
        this.targetMajor = targetMajor;
        this.targetYear = targetYear;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public CourseType getCourseType() { return courseType; }
    public String getTargetMajor() { return targetMajor; }
    public int getTargetYear() { return targetYear; }

    @Override
    public String toString() {
        return "[" + courseCode + "] " + courseName + " (" + credits + " ECTS, " + courseType + ")";
    }
}