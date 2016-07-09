package com.example.yql.todoapp;

import android.app.Application;

import com.example.yql.todoapp.crash.NCrashHandler;

/**
 * Created by yanqilong on 6/30/16.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NCrashHandler crashHandler = NCrashHandler.getInstance();
        crashHandler.setDefaultCrashHandler(getApplicationContext());
    }
}
