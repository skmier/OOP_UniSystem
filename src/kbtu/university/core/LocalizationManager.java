package kbtu.university.core;

import kbtu.university.enums.Language;

import java.io.Serializable;

public class LocalizationManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private static LocalizationManager instance;
    private Language currentLanguage = Language.EN;

    private LocalizationManager() {}

    public static synchronized LocalizationManager getInstance() {
        if (instance == null) {
            instance = new LocalizationManager();
        }
        return instance;
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    public String getString(String en, String kz, String ru) {
        switch (currentLanguage) {
            case KZ: return kz;
            case RU: return ru;
            case EN:
            default: return en;
        }
    }
}
