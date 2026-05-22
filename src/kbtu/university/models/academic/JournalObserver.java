package kbtu.university.models.academic;

import kbtu.university.models.reserach.ResearchPaper;

public interface JournalObserver {
    void notifyNewPaper(String journalName, ResearchPaper paper);
}
