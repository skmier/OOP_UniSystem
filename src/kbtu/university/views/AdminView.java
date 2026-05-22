package kbtu.university.views;

import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.Language;
import kbtu.university.enums.UserType;
import kbtu.university.factory.UserFactory;
import kbtu.university.models.users.Admin;
import kbtu.university.models.users.Student;
import kbtu.university.models.users.User;

import java.util.Scanner;

public class AdminView implements ConsoleView {
    private final Admin admin;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalizationManager lm = LocalizationManager.getInstance();

    public AdminView(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void render() {
        while (true) {
            System.out.println("\n--- " + lm.getString("ADMIN MENU", "ӘКІМШІ МЕНЮСІ", "МЕНЮ АДМИНИСТРАТОРА") + " ---");
            System.out.println("1. " + lm.getString("Add User", "Пайдаланушы қосу", "Добавить пользователя"));
            System.out.println("2. " + lm.getString("Remove User", "Пайдаланушыны жою", "Удалить пользователя"));
            System.out.println("3. " + lm.getString("Update User", "Пайдаланушыны жаңарту", "Обновить пользователя"));
            System.out.println("4. " + lm.getString("View All Users", "Барлық пайдаланушыларды көру", "Просмотр всех пользователей"));
            System.out.println("5. " + lm.getString("Make Researcher", "Зерттеуші жасау", "Назначить исследователем"));
            System.out.println("6. " + lm.getString("View Logs", "Логтарды көру", "Просмотр логов"));
            System.out.println("7. " + lm.getString("Logout", "Шығу", "Выйти"));
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createUser();
                case "2" -> removeUser();
                case "3" -> updateUser();
                case "4" -> admin.viewAllUsers();
                case "5" -> admin.viewActionLogs();
                case "7" -> { return; }
                default -> System.out.println(lm.getString("Invalid choice!", "Қате таңдау!", "Неверный выбор!"));
            }
        }
    }

    private void createUser() {
        try {
            System.out.print("Type (STUDENT, TEACHER, ADMIN, TECH_SUPPORT, MANAGER): ");
            UserType type = UserType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Login: "); String login = scanner.nextLine();
            System.out.print("Password: "); String pass = scanner.nextLine();

            User newUser = UserFactory.createUser(type, id, login, pass, name, "");
            admin.addUser(newUser);
        } catch (Exception e) {
            System.out.println(lm.getString("Error during creation!", "Құру кезінде қате!", "Ошибка при создании!"));
        }
    }

    private void removeUser() {
        System.out.print(lm.getString("Enter User ID to remove: ", "Жою үшін ID енгізіңіз: ", "Введите ID для удаления: "));
        admin.removeUser(scanner.nextLine());
    }

    private void updateUser() {
        System.out.print(lm.getString("Enter User ID to update: ", "Жаңарту үшін ID енгізіңіз: ", "Введите ID для обновления: "));
        String id = scanner.nextLine();
        System.out.print("New Name: "); String name = scanner.nextLine();
        System.out.print("New Password: "); String pass = scanner.nextLine();
        System.out.print("New Year (for student): ");
        int year = Integer.parseInt(scanner.nextLine());

        admin.updateUserData(id, name, pass, year);
    }
}
