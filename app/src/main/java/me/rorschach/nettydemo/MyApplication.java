package me.rorschach.nettydemo;

import android.app.Application;

/**
 * Created by lei on 16-6-8.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
    }
}
