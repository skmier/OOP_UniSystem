package kbtu.university.models.academic;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAtt;
    private double secondAtt;
    private double finalExam;

    public Mark() {
        this.firstAtt = 0.0;
        this.secondAtt = 0.0;
        this.finalExam = 0.0;
    }

    public double getTotalMark() {
        return finalExam + secondAtt + firstAtt;
    }

    public String grade() {
        double total = getTotalMark();
        if (total >= 94.5 && total <= 100) return "A";
        else if (total >= 89.5 && total <= 94.4) return "A-";
        else if (total >= 84.5 && total <= 89.4) return "B+";
        else if (total >= 79.5 && total <= 84.4) return "B";
        else if (total >= 74.5 && total <= 80.4) return "B-";
        else if (total >= 69.5 && total <= 74.4) return "C+";
        else if (total >= 64.5 && total <= 69.4) return "C";
        else if (total >= 60.5 && total <= 65.4) return "C-";
        else if (total >= 54.5 && total <= 60.4) return "D+";
        else if (total >= 49.5 && total <= 55.4) return "D";
        else return "F";
    }

    public double getGpaValue() {
        String letter = grade();
        switch (letter) {
            case "A":
                return 4.0;
            case "A-":
                return 3.67;
            case "B+":
                return 3.33;
            case "B":
                return 3.0;
            case "B-":
                return 2.67;
            case "C+":
                return 2.33;
            case "C":
                return 2.0;
            case "C-":
                return 1.67;
            case "D+":
                return 1.33;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }

    public double getFirstAtt() {
        return firstAtt;
    }

    public double getSecondAtt() {
        return secondAtt;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFirstAtt(double firstAtt) {
        this.firstAtt = firstAtt;
    }

    public void setSecondAtt(double secondAtt) {
        this.secondAtt = secondAtt;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "firstAtt=" + firstAtt +
                ", secondAtt=" + secondAtt +
                ", finalExam=" + finalExam +
                '}';
    }
}
