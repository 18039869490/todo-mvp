package com.example.yql.todoapp.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.yql.todoapp.BaseAppCompatActivity;
import com.example.yql.todoapp.Injection;
import com.example.yql.todoapp.R;
import com.example.yql.todoapp.util.ActivityUtils;
import com.example.yql.todoapp.util.EspressoIdlingResource;

/**
 * Displays an add or edit task screen.
 * Author：wave
 * Date：2016/5/31
 * Description：
 */
public class AddEditTaskActivity extends BaseAppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

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

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        String taskId = null;
        if(addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance();
            if(getIntent().hasExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)) {
                taskId = getIntent().getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID);
                actionBar.setTitle(R.string.edit_task);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
            }else {
                actionBar.setTitle(R.string.add_task);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTaskFragment, R.id.content_frame);
        }

        //Create the presenter
        new AddEditTasksPresenter(taskId,
                Injection.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_addtask;
    }

    @Override
    protected void resetTranslucentStatus() {
        setTranslucentStatus(2, true, R.color.colorPrimary);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
