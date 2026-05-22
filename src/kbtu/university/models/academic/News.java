package kbtu.university.models.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private boolean isResearch;
    private Date date;
    private final List<String> comments = new ArrayList<>();

    public News(String title, String content) {
        this.title = title;
        this.content = content;
        this.isResearch = false;
        this.date = new Date();
    }

    public void addComment(String userAndComment) {
        this.comments.add(userAndComment);
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public boolean isResearch() { return isResearch; }
    public List<String> getComments() { return comments; }

    public void setResearch(boolean research) {
        isResearch = research;
    }

    @Override
    public String toString() {
        String prefix = isResearch ? "[RESEARCH NEWS *PINNED*] " : "[NEWS] ";
        return prefix + title + " (" + date + ")\n   " + content;
    }
}
