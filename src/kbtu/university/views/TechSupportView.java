package kbtu.university.views;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.models.academic.Complaint;
import kbtu.university.models.users.TechSupportSpecialist;

import java.util.Scanner;

public class TechSupportView implements ConsoleView {
    private final TechSupportSpecialist tech;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalizationManager lm = LocalizationManager.getInstance();

    public TechSupportView(TechSupportSpecialist tech) {
        this.tech = tech;
    }

    @Override
    public void render() {
        while (true) {
            System.out.println("\n=== " + lm.getString("TECH SUPPORT MENU", "ТЕХ. ҚОЛДАУ МӘЗІРІ", "МЕНЮ ТЕХПОДДЕРЖКИ") + " ===");
            System.out.println("1. " + lm.getString("View Complaints", "Шағымдарды көру", "Просмотр жалоб"));
            System.out.println("2. " + lm.getString("Resolve Complaint", "Шағымды шешу", "Решить жалобу"));
            System.out.println("3. " + lm.getString("Logout", "Шығу", "Выйти"));
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> viewComplaints();
                case "2" -> resolveComplaint();
                case "3" -> { return; }
                default -> System.out.println(lm.getString("Invalid choice!", "Қате таңдау!", "Неверный выбор!"));
            }
        }
    }

    private void viewComplaints() {
        var complaints = DataStorage.getInstance().getComplaints();
        if (complaints.isEmpty()) {
            System.out.println(lm.getString("No complaints found.", "Шағымдар жоқ.", "Жалоб нет."));
        } else {
            System.out.println("\n--- " + lm.getString("PENDING COMPLAINTS", "КҮТІЛУДЕГІ ШАҒЫМДАР", "ТЕКУЩИЕ ЖАЛОБЫ") + " ---");
            for (Complaint c : complaints) {
                System.out.println(c);
            }
        }
    }

    private void resolveComplaint() {
        var complaints = DataStorage.getInstance().getComplaints();
        if (complaints.isEmpty()) {
            System.out.println(lm.getString("No complaints to resolve.", "Шешілетін шағымдар жоқ.", "Нет жалоб для решения."));
            return;
        }

        System.out.println("\n--- " + lm.getString("SELECT COMPLAINT TO RESOLVE", "ШЕШЕТІН ШАҒЫМДЫ ТАҢДАҢЫЗ", "ВЫБЕРИТЕ ЖАЛОБУ ДЛЯ РЕШЕНИЯ") + " ---");
        for (int i = 0; i < complaints.size(); i++) {
            System.out.println((i + 1) + ". " + complaints.get(i));
        }

        System.out.print(lm.getString("Enter number: ", "Нөмірді енгізіңіз: ", "Введите номер: "));
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < complaints.size()) {
                complaints.remove(index);
                DataStorage.getInstance().save();
                System.out.println(lm.getString("Resolved!", "Сәтті шешілді!", "Успешно решено!"));
            } else {
                System.out.println(lm.getString("Invalid number.", "Қате нөмір.", "Неверный номер."));
            }
        } catch (NumberFormatException e) {
            System.out.println(lm.getString("Invalid input.", "Қате енгізу.", "Ошибка ввода."));
        }
    }
}