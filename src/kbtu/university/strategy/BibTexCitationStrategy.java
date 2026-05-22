package kbtu.university.strategy;

import kbtu.university.models.reserach.ResearchPaper;

public class BibTexCitationStrategy implements CitationStrategy{
    @Override
    public String format(ResearchPaper paper) {
        return "@article{" + paper.getTitle().replaceAll("\\s+", "") + ", author={" + paper.getAuthorName() + "}, year={" + (paper.getPublicationDate().getYear() + 1900) + "}}";
    }
}
