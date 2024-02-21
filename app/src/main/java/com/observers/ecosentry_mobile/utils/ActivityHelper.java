package com.observers.ecosentry_mobile.utils;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class ActivityHelper {

    /**
     * Generic function to pass data to another activity
     */
    public static void moveToNextActivityWithData(Context currActivity, Class desActivity, Map<String, Object> dataMap) {
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
     * @param currAcc
     * @param desAcc
     */
    public static void moveToNextActivity(Context currAcc, Class desAcc) {
        ActivityHelper.moveToNextActivityWithData(currAcc, desAcc, null);
    }
}
