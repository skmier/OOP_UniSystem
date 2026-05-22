package kbtu.university.models.academic;

import kbtu.university.exceptions.NotAResearcherException;
import kbtu.university.models.users.Researcher;
import kbtu.university.models.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String topic;
    private final List<ResearchPaper> publishedPapers = new ArrayList<>();
    private final List<User> participants = new ArrayList<>();

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public void joinProject(User user) {
        if (!(user instanceof Researcher)) {
            throw new NotAResearcherException("User " + user.getName()  + " is not Researcher");
        }

        participants.add(user);
        ((Researcher) user).getProjects().add(this);
    }

    public void addPublishedPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
    }

    public String getTopic() {
        return topic;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public List<User> getParticipants() {
        return participants;
    }
}
