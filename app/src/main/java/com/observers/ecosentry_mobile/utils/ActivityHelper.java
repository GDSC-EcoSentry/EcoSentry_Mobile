package com.observers.ecosentry_mobile.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.observers.ecosentry_mobile.controllers.authentication.LoginActivity;
import com.observers.ecosentry_mobile.models.user.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class ActivityHelper {

    /**
     * Generic function to pass data to another activity
     */
    public static void moveToNextActivity(Activity currActivity, Class desActivity, Map<String, Object> dataMap) {
        Intent intent = new Intent(currActivity, desActivity);

        if (dataMap != null && !dataMap.isEmpty()) {
            dataMap.forEach((key, value) -> {
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof Integer) {
                    intent.putExtra(key, (int) value);
                } else if (value instanceof Boolean) {
                    intent.putExtra(key, (boolean) value);
                } else if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) value);
                } else {
                    // Handle other supported types or throw an exception
                }
            });
        }

        // Start the next activity
        currActivity.startActivity(intent);

        // Prevent from swiping back to previous activity
//        currActivity.finish();
    }


    /**
     * Pass a data on Intent to the next Activity
     *
     * @param key
     * @param user
     * @param currAcc
     * @param desAcc
     * @param <T>
     */
    public static <T> void sendDataToNextActivity(String key, T user, Activity currAcc, Class desAcc) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, user);
        ActivityHelper.moveToNextActivity(currAcc, desAcc, data);
    }
}
