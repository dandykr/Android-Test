package com.bri.ojt.Util;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class OJTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init Preferences
        try {
            Consts.getInstance().setsPreferences(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(this);
    }
}
