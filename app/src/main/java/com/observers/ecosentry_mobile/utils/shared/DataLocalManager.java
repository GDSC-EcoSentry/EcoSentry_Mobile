package com.observers.ecosentry_mobile.utils.shared;

import android.content.Context;

import com.google.gson.Gson;
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

    /**
     * Set user information to the Shared Preference
     *
     * @param user
     */
    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().sharedPreference.putValue(PREF_USER, strJsonUser);
    }

    /**
     * Get user object stored inside the Shared Preference
     *
     * @return user object
     */
    public static User getUser() {
        String strJsonUser = DataLocalManager.getInstance().sharedPreference.getStringValue(PREF_USER);
        Gson gSon = new Gson();
        return gSon.fromJson(strJsonUser, User.class);
    }
}
