package com.observers.ecosentry_mobile.utils.shared;

import android.content.Context;

import com.observers.ecosentry_mobile.models.user.User;

import java.util.HashSet;
import java.util.Set;

public class DataLocalManager {


    // ======================
    // == Fields
    // ======================
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_USER = "PREF_USER";
    private static DataLocalManager instance;
    private SharedPreference sharedPreference;


    // ======================
    // == Constructor
    // ======================

    /**
     * Apply Singleton to create only 1 instance of class
     */
    private DataLocalManager() {
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.sharedPreference = new SharedPreference(context);
    }

    // ======================
    // == Methods
    // ======================

    /**
     * Set preference determince first time installs the app
     *
     * @param isFirst
     */
    public static void setFirstInstalled(boolean isFirst) {
        DataLocalManager.getInstance().sharedPreference.putValue(PREF_FIRST_INSTALL, isFirst);
    }

    /**
     * Get preference determine either first installed app or not
     */
    public static boolean getFirstInstalled() {
        return DataLocalManager.getInstance().sharedPreference.getBooleanValue(PREF_FIRST_INSTALL);
    }

    public static void setUser(User user) {
        Set<String> value = new HashSet<>();
        value.add(user.getEmail());
        value.add(user.getPassword());
        value.add(user.getUsername());
        DataLocalManager.getInstance().sharedPreference.putValue(PREF_USER, value);
    }

    public static Set<String> getUser() {
        return DataLocalManager.getInstance().sharedPreference.getStringSetValue(PREF_USER);
    }
}
