package kbtu.university.comporators;

import kbtu.university.models.reserach.ResearchPaper;

import java.util.Comparator;

public class ResearchComparators {
    public static class PaperByCitationsComparator implements Comparator<ResearchPaper> {
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return Integer.compare(p2.getCitations(), p1.getCitations());
    }
    }
    public static class PaperByDateComparator implements Comparator<ResearchPaper> {
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return p1.getPublicationDate().compareTo(p2.getPublicationDate());
        }
    }

    public static class PaperByPagesComparator implements Comparator<ResearchPaper> {
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p1.getPages(), p2.getPages());
        }
    }

}
