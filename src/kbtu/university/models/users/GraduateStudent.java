package kbtu.university.models.users;

import kbtu.university.exceptions.LowHIndexException;

public class GraduateStudent extends Student implements Researcher{
    private static final long serialVersionUID = 1L;

    private Researcher supervisor;
    private final ResearcherDelegate researcherDelegate = new ResearcherDelegate();

    public GraduateStudent(String id, String login, String password, String name, String major, int yearOfStudy) {
        super(id, login, password, name, major, yearOfStudy);
    }

    public void setSupervisor(Researcher supervisor) {
        if (supervisor == null) return;
        if (supervisor.calculateHIndex() < 3) {
            throw new LowHIndexException("[ERROR] Cannot assign supervisor " +
                    ((User)supervisor).getName() + ". Their h-index is " + supervisor.calculateHIndex() + " (minimum required is 3).");
        }
        this.supervisor = supervisor;
        System.out.println("[SUCCESS] Supervisor assigned successfully.");
    }


    @Override public int calculateHIndex() { return researcherDelegate.calculateHIndex(); }
    @Override public void printPapers(java.util.Comparator<kbtu.university.models.academic.ResearchPaper> c) { researcherDelegate.printPapers(c); }
    @Override public java.util.List<kbtu.university.models.academic.ResearchPaper> getPapers() { return researcherDelegate.getPapers(); }
    @Override public java.util.List<kbtu.university.models.academic.ResearchProject> getProjects() { return researcherDelegate.getProjects(); }
    @Override public void addPaper(kbtu.university.models.academic.ResearchPaper paper) { researcherDelegate.addPaper(paper); }

    public Researcher getSupervisor() { return supervisor; }
}
