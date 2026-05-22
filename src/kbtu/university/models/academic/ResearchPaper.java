package kbtu.university.models.academic;

import kbtu.university.enums.CitationFormat;

import java.io.Serializable;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper>{
    private static final long serialVersionUID = 1L;

    private String title;
    private String authors;
    private String journal;
    private int pages;
    private int year;
    private int citations;

    public ResearchPaper(String title, String authors, String journal, int pages, int year) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.year = year;
        this.citations = 0;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.BIBTEX) {
            return String.format(
                    "@article{paper_%s,\n" +
                            "  author = {%s},\n" +
                            "  title = {%s},\n" +
                            "  journal = {%s},\n" +
                            "  year = {%d},\n" +
                            "  pages = {%d},\n" +
                            "  doi = {%s}\n" +
                            "}",
                     authors, title, journal, year, pages
            );
        } else {
            return String.format("%s. (%d). %s. %s, %d p.  (Citations: %d)",
                    authors, year, title, journal, pages, citations);
        }
    }

    public void addCitation() {
        this.citations++;
    }
    @Override
    public int compareTo(ResearchPaper o) {
        return Integer.compare(o.getCitations(), this.citations);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public int getCitations() {
        return citations;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }
}
