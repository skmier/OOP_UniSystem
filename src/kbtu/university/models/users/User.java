package kbtu.university.models.users;

import kbtu.university.core.LocalizationManager;
import kbtu.university.enums.Language;
import kbtu.university.models.academic.JournalObserver;
import kbtu.university.models.reserach.ResearchPaper;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable,Comparable<User> , JournalObserver {
    private static final long serialVersionUID = 1L;

    private String id;
    private String login;
    private String password;
    private String name;
    private Language language;

    public User(String id, String login, String password, String name){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.language = Language.EN;
    }

    public boolean isAuthenticated(String loginOrId, String password){
        boolean idMatch = this.id != null && this.id.equals(loginOrId);
        boolean loginMatch = this.login != null && this.login.equals(loginOrId);
        boolean passMatch = this.password != null && this.password.equals(password);

        return (idMatch || loginMatch) && passMatch;
    }

    @Override
    public void notifyNewPaper(String journalName, ResearchPaper paper) {
        LocalizationManager lm = LocalizationManager.getInstance();

        String msgEn = "\n[NOTIFICATION FOR " + name.toUpperCase() + "] New paper in '" + journalName + "': " + paper.getTitle();
        String msgKz = "\n[" + name.toUpperCase() + " ҮШІН ХАБАРЛАМА] '" + journalName + "' журналында жаңа мақала: " + paper.getTitle();
        String msgRu = "\n[УВЕДОМЛЕНИЕ ДЛЯ " + name.toUpperCase() + "] Новая статья в '" + journalName + "': " + paper.getTitle();

        System.out.println(lm.getString(msgEn, msgKz, msgRu));
    }

    @Override
    public String toString() {
        return "User: " + this.name + " id: " + this.id + " language: " + this.language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) || Objects.equals(login, user.login);
    }

    @Override
    public int compareTo(User o) {
        if (o == null) return 1;
        return this.id.compareTo(o.getId());
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Language getLanguage() {
        return language;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeLanguage(Language language) {
        this.language = language;
    }


}
