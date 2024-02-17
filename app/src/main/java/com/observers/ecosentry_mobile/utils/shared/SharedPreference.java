package com.observers.ecosentry_mobile.utils.shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPreference {

    // ======================
    // == Fields
    // ======================
    private static final String SHARED_PREFERENCE = "SHARED_PREFERENCE";
    private Context mContext;

    // ======================
    // == Constructor
    // ======================
    public SharedPreference(Context mContext) {
        this.mContext = mContext;
    }

    // =========================
    // == putValue Overloading
    // =========================
    public void putValue(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putValue(String key, long value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putValue(String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putValue(String key, float value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putValue(String key, Set<String> values) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    // ==============================
    // == Get Preference On Datatype
    // ==============================
    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(key, false);
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "");
    }

    public Set<String> getStringSetValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getStringSet(key, new HashSet<>());
    }

    public long getLongValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getLong(key, 0);
    }

    public int getIntValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getInt(key, 0);
    }

    public float getFloatValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE,
                Context.MODE_PRIVATE);

        return sharedPreferences.getFloat(key, 0f);
    }
}
