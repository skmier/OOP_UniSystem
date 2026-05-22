package kbtu.university.strategy;

import kbtu.university.models.reserach.ResearchPaper;

public class PlainCitationStrategy implements CitationStrategy {
    @Override
    public String format(ResearchPaper paper) {
        return paper.getAuthorName() + ". (" + (paper.getPublicationDate().getYear() + 1900) + "). " + paper.getTitle() + ".";
    }
}