package com.example.yql.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yql.todoapp.crash.CrashPrinter;
import com.example.yql.todoapp.crash.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings settings = Settings.getInstance().init();
        CrashPrinter crashPrinter = new CrashPrinter();
        crashPrinter.printCrashLog();
    }
}
