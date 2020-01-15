package com.jk.rrpt;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class RRPT extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
