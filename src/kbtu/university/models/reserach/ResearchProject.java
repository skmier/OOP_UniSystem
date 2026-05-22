package kbtu.university.models.reserach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;
    private Researcher projectLeader;
    private final List<Researcher> participants = new ArrayList<>();
    private final List<ResearchPaper> publishedPapers = new ArrayList<>();

    public ResearchProject(String topic, Researcher projectLeader) {
        if (projectLeader == null) {
            throw new IllegalArgumentException("Project leader cannot be null");
        }
        this.topic = topic;
        this.projectLeader = projectLeader;
        this.participants.add(projectLeader);
    }

    public void addParticipant(Researcher researcher) {
        if (researcher != null && !participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void addPublishedPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }


    public String getTopic() { return topic; }
    public Researcher getProjectLeader() { return projectLeader; }
    public List<Researcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "topic='" + topic + '\'' +
                ", projectLeader=" + projectLeader +
                ", participants=" + participants +
                ", publishedPapers=" + publishedPapers +
                '}';
    }
}
