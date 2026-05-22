package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.models.academic.Message;
import kbtu.university.models.academic.Request;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private static final long serialVersionUID = 1L;
    private final List<Message> inboxMessages = new ArrayList<>();
    private boolean isResearcher = false;
    public Employee(String id, String login, String password, String name) {
        super(id, login, password, name);
    }
    public boolean isResearcher() {
        return isResearcher;
    }
    public void setResearcher(boolean researcher) {
        this.isResearcher = researcher;
    }

    public void sendRequest(String content) {
        Request newReq = new Request(this.getId(), content);
        DataStorage.getInstance().getRequests().add(newReq);
        DataStorage.getInstance().save();

        LocalizationManager lm = LocalizationManager.getInstance();
        System.out.println(lm.getString(
                "[SUCCESS] Request sent successfully.",
                "[СӘТТІ] Өтініш сәтті жіберілді.",
                "[УСПЕШНО] Запрос успешно отправлен."
        ));
    }

    public void sendMessage(Employee recipient, String content) {
        LocalizationManager lm = LocalizationManager.getInstance();

        if (recipient == null || content == null || content.trim().isEmpty()) {
            System.out.println(lm.getString(
                    "[ERROR] Invalid recipient or empty message",
                    "[ҚАТЕ] Алушы қате немесе бос хабарлама",
                    "[ОШИБКА] Неверный получатель или пустое сообщение"
            ));
            return;
        }
        Message msg = new Message(this.getId(), this.getName(), content);
        recipient.getInbox().add(msg);

        DataStorage.getInstance().getActionLogs().add(
                new UserActionLog(this.getId(), "SEND_MESSAGE", "To: " + recipient.getName())
        );
        DataStorage.getInstance().save();

        System.out.println(lm.getString(
                "[SUCCESS] Message successfully sent to " + recipient.getName(),
                "[СӘТТІ] Хабарлама " + recipient.getName() + " пайдаланушыға сәтті жіберілді",
                "[УСПЕШНО] Сообщение успешно отправлено для " + recipient.getName()
        ));
    }

    public void viewInbox() {
        LocalizationManager lm = LocalizationManager.getInstance();

        System.out.println("\n=== " + lm.getString("INBOX FOR ", "КІРІС ЖӘШІГІ: ", "ВХОДЯЩИЕ ДЛЯ ") + getName().toUpperCase() + " ===");

        if (inboxMessages.isEmpty()) {
            System.out.println(lm.getString(
                    "Your inbox is empty.",
                    "Сіздің кіріс жәшігіңіз бос.",
                    "Ваш почтовый ящик пуст."
            ));
            return;
        }

        for (Message msg : inboxMessages) {
            System.out.println(msg);
            msg.markAsRead();
        }
        System.out.println("=================================");
    }

    public List<Message> getInbox() { return inboxMessages; }
}