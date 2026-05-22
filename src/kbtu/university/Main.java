package kbtu.university;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.Language;
import kbtu.university.models.reserach.Researcher;
import kbtu.university.models.users.*;
import kbtu.university.views.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataStorage ds = DataStorage.getInstance();
        Scanner scanner = new Scanner(System.in);
        LocalizationManager lm = LocalizationManager.getInstance();

        System.out.println("Select language / Тілді таңдаңыз / Выберите язык:");
        System.out.println("1. English | 2. Қазақ тілі | 3. Русский");
        System.out.print("> ");
        String langChoice = scanner.nextLine();

        switch (langChoice) {
            case "2" -> lm.setLanguage(Language.KZ);
            case "3" -> lm.setLanguage(Language.RU);
            default -> lm.setLanguage(Language.EN);
        }

        if (!ds.hasAdmin()) {
            System.out.println(lm.getString("--- SYSTEM INITIALIZATION ---", "--- ЖҮЙЕНІ ИНИЦИАЛИЗАЦИЯЛАУ ---", "--- ИНИЦИАЛИЗАЦИЯ СИСТЕМЫ ---"));
            System.out.println(lm.getString("No admin found. Create the first administrator:", "Әкімші табылмады. Алғашқы әкімшіні жасаңыз:", "Администратор не найден. Создайте первого администратора:"));

            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print(lm.getString("Name: ", "Аты-жөні: ", "Имя: ")); String name = scanner.nextLine();
            System.out.print("Login: "); String login = scanner.nextLine();
            System.out.print("Password: "); String pass = scanner.nextLine();

            Admin firstAdmin = new Admin(id, login, pass, name);
            ds.getUsers().add(firstAdmin);
            ds.save();
            System.out.println(lm.getString("Admin created! Please restart.", "Әкімші жасалды! Жүйені қайта іске қосыңыз.", "Админ создан! Перезапустите программу."));
            System.exit(0);
        }

        System.out.println(lm.getString("Welcome to KBTU University System!", "KBTU Университет жүйесіне қош келдіңіз!", "Добро пожаловать в систему КБТУ!"));

        while (true) {
            System.out.println("\n--- " + lm.getString("LOGIN", "КІРУ", "ВХОД") + " ---");
            System.out.print(lm.getString("Login: ", "Логин: ", "Логин: "));
            String login = scanner.nextLine();
            System.out.print(lm.getString("Password: ", "Құпия сөз: ", "Пароль: "));
            String pass = scanner.nextLine();

            User user = ds.getUsers().stream()
                    .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(pass))
                    .findFirst().orElse(null);

            if (user == null) {
                System.out.println(lm.getString("Invalid credentials.", "Деректер қате.", "Неверные данные."));
                continue;
            }
            if (user instanceof Admin) new AdminView((Admin) user).render();
            else if (user instanceof GraduateStudent) {
                new ResearcherView((GraduateStudent) user).render();}
            else if (user instanceof Student) new StudentView((Student) user).render();
            else if (user instanceof Teacher) new TeacherView((Teacher) user).render();
            else if (user instanceof Manager) new ManagerView((Manager) user).render();
            else if (user instanceof TechSupportSpecialist) new TechSupportView((TechSupportSpecialist) user).render();


            ds.save();
            System.out.println(lm.getString("Session closed.", "Сессия аяқталды.", "Сессия завершена."));
        }
    }
}
