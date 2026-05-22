package kbtu.university.models.users;

import kbtu.university.enums.CitationFormat;
import kbtu.university.models.academic.ResearchPaper;
import kbtu.university.models.academic.ResearchProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResearcherDelegate implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public int calculateHIndex() {
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort((p1, p2) -> Integer.compare(p2.getCitations(), p1.getCitations()));

        int hIndex = 0;
        for (int i = 0; i < sortedPapers.size(); i++) {
            if (sortedPapers.get(i).getCitations() >= i + 1) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
        return hIndex;
    }

    public void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(c);
        for (ResearchPaper paper : sorted) {
            System.out.println(paper.getCitation(CitationFormat.PLAIN_TEXT));
        }
    }


    public List<ResearchPaper> getPapers() { return papers; }
    public List<ResearchProject> getProjects() { return projects; }
    public void addPaper(ResearchPaper paper) { papers.add(paper); }
}
