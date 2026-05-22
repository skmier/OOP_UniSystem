package kbtu.university.models.reserach;

import kbtu.university.enums.CitationFormat;
import kbtu.university.strategy.CitationStrategy;

import java.io.Serializable;
import java.util.Date;

public class ResearchPaper implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String authorName;
    private int citations;
    private Date publicationDate;
    private int pages;


    public ResearchPaper(String title, String authorName) {
        this.title = title;
        this.authorName = authorName;
        this.citations = 0;
        this.publicationDate = new Date();
    }



    public void addCitation() {
        this.citations++;
    }

    public String getCitation(CitationStrategy strategy) {
        return strategy.format(this);
    }


    public int getPages() { return pages; }

    public String getTitle() { return title; }
    public String getAuthorName() { return authorName; }
    public int getCitations() { return citations; }
    public Date getPublicationDate() { return publicationDate; }

    @Override
    public String toString() {
        return "\"" + title + "\" | Author: " + authorName + " | Citations: " + citations;
    }
}
