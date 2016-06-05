package com.example.yql.todoapp.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.yql.todoapp.BaseAppCompatActivity;
import com.example.yql.todoapp.R;

/**
 * Displays an add or edit task screen.
 * Author：wave
 * Date：2016/5/31
 * Description：
 */
public class AddEditTaskActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set up the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_addtask;
    }

    @Override
    protected void resetTranslucentStatus() {
        setTranslucentStatus(1, false, 0);
    }
}
