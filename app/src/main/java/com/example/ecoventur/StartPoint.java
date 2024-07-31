package com.example.ecoventur;

import android.app.Application;

import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.google.firebase.FirebaseApp;

public class StartPoint extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseHandler db = new DataBaseHandler(this);
        FirebaseApp.initializeApp(this);
    }
}
