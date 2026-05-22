package kbtu.university.models.users;

import kbtu.university.core.DataStorage;
import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.RequestStatus;
import kbtu.university.models.academic.Complaint;

public class TechSupportSpecialist extends Employee{
    private static final long serialVersionUID = 1L;

    public TechSupportSpecialist(String id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public void updateComplaintStatus(Complaint complaint, RequestStatus newStatus) {
        LocalizationManager lm = LocalizationManager.getInstance();
        if (complaint != null) {
            complaint.setStatus(newStatus);
            DataStorage.getInstance().save();

            System.out.println(lm.getString(
                    "Status updated to: " + newStatus,
                    "Статус келесіге өзгертілді: " + newStatus,
                    "Статус обновлен на: " + newStatus
            ));
        }
    }

    public void viewAllComplaints() {
        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage db = DataStorage.getInstance();

        System.out.println("\n=== " + lm.getString("SYSTEM COMPLAINTS", "ЖҮЙЕ ШАҒЫМДАРЫ", "СИСТЕМНЫЕ ЖАЛОБЫ") + " ===");

        if (db.getComplaints().isEmpty()) {
            System.out.println(lm.getString("No active complaints.", "Белсенді шағымдар жоқ.", "Нет активных жалоб."));
            return;
        }

        for (Complaint c : db.getComplaints()) {
            System.out.println(c);
        }
    }

    public void resolveComplaint(Complaint complaint) {
        LocalizationManager lm = LocalizationManager.getInstance();
        DataStorage db = DataStorage.getInstance();

        if (complaint == null) {
            System.out.println(lm.getString("[ERROR] Invalid complaint!", "[ҚАТЕ] Шағым қате!", "[ОШИБКА] Неверная жалоба!"));
            return;
        }

        boolean removed = db.getComplaints().remove(complaint);

        if (removed) {
            db.getActionLogs().add(new UserActionLog(
                    this.getId(),
                    "RESOLVE_COMPLAINT",
                    "Resolved ticket text: " + complaint.getText()
            ));
            db.save();

            System.out.println(lm.getString(
                    "[SUCCESS] Complaint resolved and closed.",
                    "[СӘТТІ] Шағым шешілді және жабылды.",
                    "[УСПЕШНО] Жалоба решена и закрыта."
            ));
        } else {
            System.out.println(lm.getString(
                    "[ERROR] Complaint not found in the system.",
                    "[ҚАТЕ] Шағым жүйеде табылмады.",
                    "[ОШИБКА] Жалоба не найдена в системе."
            ));
        }
    }

    @Override
    public String toString() {
        return "TechSupport: " + getName();
    }
}
