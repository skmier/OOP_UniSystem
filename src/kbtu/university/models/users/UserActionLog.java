package kbtu.university.models.users;

import java.io.Serializable;
import java.util.Date;

public class UserActionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String adminId;
    private String actionType;
    private String details;

    public UserActionLog(String adminId, String actionType, String details){
        this.timestamp = new Date();
        this.adminId = adminId;
        this.actionType = actionType;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getDetails() {
        return details;
    }
    @Override
    public String toString() {
        return "[" + timestamp + "] Admin ID: " + adminId + " | Action: " + actionType + " | Details: " + details;
    }

}
