package kbtu.university.models.users;

import kbtu.university.core.DataStorage;

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

    public void addUser(User user){
        DataStorage db = DataStorage.getInstance();
        if(!db.getUsers().contains(user)){
            db.getUsers().add(user);
            saveAction("CREATE","NEW USER " + user.getName() + " created ");
            System.out.println("[SUCCESS] New user " + user.getName() + " is created");
        }else {
            System.out.println("[ERROR] User with such name is exist");
        }
    }

    public void removeUser(String id){
        DataStorage db = DataStorage.getInstance();
        boolean removed = false;
        for (int i = db.getUsers().size() - 1; i >= 0; i--){
            User user = db.getUsers().get(i);
            if(user.getId().equals(id)){
                db.getUsers().remove(i);
                removed = true;
                break;
            }
        }

        if(removed){
            saveAction("DELETE","User with id: " + id + " deleted");
            db.save();
            System.out.println("[SUCCESS] user is deleted");
        }else {
            System.out.println("[ERROR] user with such id doesnt exist");
        }
    }

    public void updateUser(String id, String newName, String newPassword){
        DataStorage db = DataStorage.getInstance();
        User user = null;
        for(User u : db.getUsers()){
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }


        if(user != null){
            String oldName = user.getName();

            user.setName(newName);
            user.setPassword(newPassword);

            saveAction("UPDATE","User: "+ oldName + "`s data is updated");
            System.out.println("[SUCCESS] User data updated");
        }else {
            System.out.println("[ERROR] user with such id doesnt exist");
        }
    }

    public void viewAllUsers(){
        DataStorage db = DataStorage.getInstance();
        System.out.println("\n=============================================");
        System.out.println("                 All users                   ");
        System.out.println("=============================================");
        if(!db.getUsers().isEmpty()){
            for (User u: db.getUsers()){
                System.out.println(u);
            }
            System.out.println("=============================================");
        }
        else {
            System.out.println("User table is empty");
        }
    }

    public void viewAllActions(){
        DataStorage db = DataStorage.getInstance();
        System.out.println("\n=============================================");
        System.out.println("                 All actions                 ");
        System.out.println("=============================================");

        if(!db.getActionLogs().isEmpty()){
            for (UserActionLog u: db.getActionLogs()){
                System.out.println(u);
            }
            System.out.println("=============================================");
        }
        else {
            System.out.println("Action list is empty");
        }
    }
}
