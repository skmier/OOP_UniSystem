package kbtu.university.models.users;

import kbtu.university.models.academic.ResearchPaper;
import kbtu.university.models.academic.ResearchProject;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> c);
    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();
    void addPaper(ResearchPaper paper);
}