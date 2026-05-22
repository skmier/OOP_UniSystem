package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.exceptions.LowHIndexException;
import kbtu.university.models.academic.JournalObserver;
import kbtu.university.models.reserach.ResearchJournal;
import kbtu.university.models.reserach.ResearchPaper;
import kbtu.university.models.reserach.Researcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraduateStudent extends Student implements Researcher, JournalObserver {
    private static final long serialVersionUID = 1L;

    private final List<ResearchPaper> papers = new ArrayList<>();

    public GraduateStudent(String id, String login, String password, String name, String major, int yearOfStudy) {
        super(id, login, password, name, major, yearOfStudy);
    }

    @Override
    public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
        papers.add(paper);
        journal.addPaper(paper);
        DataStorage.getInstance().getActionLogs().add(new UserActionLog(this.getId(), "PUBLISH_PAPER", "Graduate Student published: " + paper.getTitle()));
        DataStorage.getInstance().save();
    }

    @Override
    public List<ResearchPaper> getMyPapers() { return papers; }

    @Override
    public int calculateHIndex() {
        if (papers.isEmpty()) return 0;
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper p : papers) citations.add(p.getCitations());
        citations.sort(Collections.reverseOrder());

        int hIndex = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) hIndex = i + 1;
            else break;
        }
        return hIndex;
    }

    @Override
    public void notifyNewPaper(String journalName, ResearchPaper paper) {
        LocalizationManager lm = LocalizationManager.getInstance();
        String msg = lm.getString(
                "\n[RESEARCH NOTIFICATION - " + getName() + "]: New paper in journal \"" + journalName + "\" -> ",
                "\n[ҒЫЛЫМИ ХАБАРЛАНДЫРУ - " + getName() + "]: \"" + journalName + "\" журналында жаңа мақала шықты -> ",
                "\n[НАУЧНОЕ УВЕДОМЛЕНИЕ - " + getName() + "]: В журнале \"" + journalName + "\" вышла статья -> "
        );
        System.out.println(msg + paper.getTitle());
    }
}
