package kbtu.university.core;

import kbtu.university.models.users.User;
import kbtu.university.models.users.UserActionLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static DataStorage instance;
    private static final String FILE_PATH = "university_data.ser";


    private List<User> users = new ArrayList<>();
    private List<UserActionLog> actionLogs = new ArrayList<>();

    private DataStorage(){};

    public static DataStorage getInstance() {
        return instance;
    }

    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException e) {
            System.out.println("Error with saving data");
        }
    }

    private static DataStorage load(){
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return new DataStorage();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return (DataStorage) ois.readObject();
        }catch (Exception e){
            System.out.println("Load error new db is created");
            return new DataStorage();
        }

    }

    public List<User> getUsers(){
        return users;
    }

    public List<UserActionLog> getActionLogs(){
        return actionLogs;
    }
}
