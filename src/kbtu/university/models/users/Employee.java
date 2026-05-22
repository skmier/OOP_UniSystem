package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.models.academic.Message;

import java.util.ArrayList;
import java.util.List;

public  class Employee extends User{
    private static final long serialVersionUID = 1L;
    private final List<Message> inboxMessages = new ArrayList<>();

    public Employee(String id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public void sendMessage(Employee recipient, String content) {
        if (recipient == null || content == null || content.trim().isEmpty()) {
            System.out.println("[ERROR] Invalid recipient or empty message");
            return;
        }
        Message msg = new Message(this.getId(), this.getName(), content);
        recipient.getInbox().add(msg);

        DataStorage.getInstance().getActionLogs().add(new UserActionLog(this.getId(), "SEND_MESSAGE", "To: " + recipient.getName())
        );
        System.out.println("[SUCCESS] Message successfully sent to " + recipient.getName());
    }

    public void viewInbox() {
        System.out.println("\n=== INBOX FOR " + getName().toUpperCase() + " ===");
        if (inboxMessages.isEmpty()) {
            System.out.println("Your inbox is empty.");
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

