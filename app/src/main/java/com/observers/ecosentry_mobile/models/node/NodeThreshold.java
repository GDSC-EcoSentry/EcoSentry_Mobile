package com.observers.ecosentry_mobile.utils;

import com.observers.ecosentry_mobile.R;

import java.util.HashMap;

public class NodeThreshold {

    /**
     * Get Color based on thresshold
     *
     * @param data
     * @param th_1
     * @param th_2
     * @param th_3
     * @param <T>
     * @return
     */
    private static <T extends Number & Comparable<T>> int getColorOnThreshold(T data, T th_1, T th_2, T th_3) {
        if (data.compareTo(th_1) <= 0) {
            return R.color.green600;
        } else if (data.compareTo(th_2) <= 0) {
            return R.color.yellow500;
        } else {
            return R.color.red500;
        }
    }
}
