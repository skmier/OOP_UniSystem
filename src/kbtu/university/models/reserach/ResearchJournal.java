package kbtu.university.models.reserach;

import kbtu.university.models.academic.JournalObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchJournal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String journalName;
    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<JournalObserver> subscribers = new ArrayList<>();

    public ResearchJournal(String journalName) {
        this.journalName = journalName;
    }

    public void subscribe(JournalObserver observer) {
        if (observer != null && !subscribers.contains(observer)) {
            subscribers.add(observer);
        }
    }

    public void unsubscribe(JournalObserver observer) {
        subscribers.remove(observer);
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        for (JournalObserver observer : subscribers) {
            observer.notifyNewPaper(this.journalName, paper);
        }
    }

    public String getJournalName() { return journalName; }
    public List<ResearchPaper> getPapers() { return papers; }

}
