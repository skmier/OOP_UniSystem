package kbtu.university.models.reserach;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    void publishPaper(ResearchPaper paper, ResearchJournal journal);
    List<ResearchPaper> getMyPapers();
    int calculateHIndex();
    default void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> papers = getMyPapers();
        papers.sort(c);
        for (ResearchPaper p : papers) {
            System.out.println(p);
        }
    }
}
