package kbtu.university.strategy;

import kbtu.university.models.reserach.ResearchPaper;

public interface CitationStrategy {
    String format(ResearchPaper paper);
}
