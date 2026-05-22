package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;

import java.util.List;

public class Admin extends User{
    private static final long serialVersionUID = 1L;

    public Admin(String id, String login, String password, String name) {
        super(id, login, password, name);
    }

    private void saveAction(String actionType,String details){
        DataStorage db = DataStorage.getInstance();
        db.getActionLogs().add(new UserActionLog(this.getId(),actionType,details));
    }

    public void addUser(User newUser) {
        if (newUser == null) return;

        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage ds = DataStorage.getInstance();
        String login = newUser.getLogin();
        boolean loginExists = ds.getUsers().stream()
                .anyMatch(u -> u.getLogin().equalsIgnoreCase(login));

        if (loginExists) {
            System.out.println(lm.getString(
                    "[ADMIN ERROR] Login '" + login + "' is already taken!",
                    "[АДМИН ҚАТЕ] '" + login + "' логині бос емес!",
                    "[АДМИН ОШИБКА] Логин '" + login + "' уже занят!"
            ));
            return;
        }

        ds.getUsers().add(newUser);

        ds.getActionLogs().add(new UserActionLog(this.getId(), "ADD_USER", "Added user: " + login));
        ds.save();

        System.out.println(lm.getString(
                "[ADMIN SUCCESS] User (" + newUser.getName() + ") successfully added.",
                "[АДМИН] Пайдаланушы (" + newUser.getName() + ") сәтті қосылды.",
                "[ADMIN УСПЕШНО] Пользователь (" + newUser.getName() + ") успешно добавлен."
        ));
    }

    public void removeUser(String userId) {
        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage ds = DataStorage.getInstance();

        boolean removed = ds.getUsers().removeIf(u -> u.getId().equals(userId));

        if (removed) {
            saveAction("REMOVE_USER", "Removed user ID: " + userId);
            ds.save();
            System.out.println(lm.getString("[SUCCESS] User removed.", "[CӘТТІ] Өшірілді.", "[УСПЕШНО] Пользователь удален."));
        } else {
            System.out.println(lm.getString("[ERROR] Not found.", "[ҚАТЕ] Табылмады.", "[ОШИБКА] Не найден."));
        }
    }

    public void updateUserData(String id, String newName, String newPassword, int newYear) {
        DataStorage db = DataStorage.getInstance();
        LocalizationManager lm = LocalizationManager.getInstance();

        User userToUpdate = db.getUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setName(newName);
            userToUpdate.setPassword(newPassword);

            if (userToUpdate instanceof Student) {
                ((Student) userToUpdate).setYearOfStudy(newYear);
            }

            db.getActionLogs().add(new UserActionLog(this.getId(), "UPDATE_USER", "Updated data for ID: " + id));
            db.save();

            System.out.println(lm.getString(
                    "[SUCCESS] Data updated.",
                    "[СӘТТІ] Мәліметтер өзгертілді.",
                    "[УСПЕШНО] Данные обновлены."
            ));
        } else {
            System.out.println("[ERROR] User not found.");
        }
    }

    public void viewActionLogs() {
        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println("\n=== " + lm.getString("SYSTEM ACTION LOGS", "ЖҮЙЕ ЛОГТАРЫ", "СИСТЕМНЫЕ ЛОГИ ДЕЙСТВИЙ") + " ===");

        if (DataStorage.getInstance().getActionLogs().isEmpty()) {
            System.out.println(lm.getString("Logs are empty.", "Логтар бос.", "Логи пусты."));
            return;
        }

        for (UserActionLog log : DataStorage.getInstance().getActionLogs()) {
            System.out.println(log);
        }
    }



    public void viewAllUsers() {
        LocalizationManager lm = LocalizationManager.getInstance();
        List<User> users = DataStorage.getInstance().getUsers();

        System.out.println("\n=== " + lm.getString("ALL USERS", "БАРЛЫҚ ПАЙДАЛАНУШЫЛАР", "ВСЕ ПОЛЬЗОВАТЕЛИ") + " ===");
        System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "ID", "Name", "Type", "Extra");

        for (User u : users) {
            String type = u.getClass().getSimpleName();
            String info = "-";
            if (u instanceof Student s) info = "Year: " + s.getYearOfStudy();
            else if (u instanceof Employee e) info = e.isResearcher() ? "Researcher" : "Employee";

            System.out.printf("%-10s | %-15s | %-15s | %-15s\n", u.getId(), u.getName(), type, info);
        }
    }


}
