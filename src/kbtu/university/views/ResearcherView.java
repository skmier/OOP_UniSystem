package kbtu.university.views;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.models.reserach.ResearchPaper;
import kbtu.university.models.reserach.Researcher;
import kbtu.university.models.users.User;

import java.util.Scanner;

public class ResearcherView implements ConsoleView {

    private final Researcher researcher;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalizationManager lm = LocalizationManager.getInstance();

    public ResearcherView(Researcher researcher) {
        this.researcher = researcher;
    }

    @Override
    public void render() {
        while (true) {
            System.out.println("\n=== " + lm.getString("RESEARCHER MENU", "ЗЕРТТЕУШІ МӘЗІРІ", "МЕНЮ ИССЛЕДОВАТЕЛЯ") + " ===");
            System.out.println("1. " + lm.getString("View My Papers", "Мақалаларымды көру", "Мои статьи"));
            System.out.println("2. " + lm.getString("Publish Paper", "Мақала жариялау", "Опубликовать статью"));
            System.out.println("3. " + lm.getString("View H-Index", "H-индексті көру", "Посмотреть H-индекс"));
            System.out.println("4. " + lm.getString("Logout", "Шығу", "Выйти"));
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> researcher.getMyPapers().forEach(System.out::println);
                case "2" -> publishProcess();
                case "3" -> System.out.println("H-Index: " + researcher.calculateHIndex());
                case "4" -> { return; }
                default -> System.out.println("Invalid!");
            }
        }
    }

    private void publishProcess() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        String authorName = ((User) researcher).getName();

        ResearchPaper paper = new ResearchPaper(title, authorName);

        researcher.publishPaper(paper, null);

        System.out.println(lm.getString("Published successfully!", "Сәтті жарияланды!", "Успешно опубликовано!"));
    }
}