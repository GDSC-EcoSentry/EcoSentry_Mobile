package com.observers.ecosentry_mobile.controllers.application;

import android.app.Application;

import com.observers.ecosentry_mobile.utils.shared.DataLocalManager;

public class GlobalApplication extends Application {

    /**
     * Initialize the Shared Preference at the beginning of the Application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(this.getApplicationContext());
    }
}
