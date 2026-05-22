package kbtu.university.decorator;

import kbtu.university.models.reserach.ResearchJournal;
import kbtu.university.models.reserach.ResearchPaper;
import kbtu.university.models.reserach.Researcher;
import kbtu.university.models.users.Employee;
import kbtu.university.models.users.Teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearcherDecorator implements Researcher {
    protected Employee teacher;
    protected List<ResearchPaper> papers = new ArrayList<>();

    public ResearcherDecorator(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
        papers.add(paper);
        journal.addPaper(paper);
    }

    @Override
    public List<ResearchPaper> getMyPapers() {
        return papers;
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citations = papers.stream().map(ResearchPaper::getCitations).sorted(Collections.reverseOrder()).toList();
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    public String getName() { return teacher.getName(); }
}
