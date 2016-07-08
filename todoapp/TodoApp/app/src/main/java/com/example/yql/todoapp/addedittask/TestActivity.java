package com.example.yql.todoapp.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.yql.todoapp.BaseAppCompatActivity;
import com.example.yql.todoapp.R;

/**
 * Author：wave
 * Date：2016/7/8
 * Description：
 */
public class TestActivity extends BaseAppCompatActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void resetTranslucentStatus() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.bt_jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddEditTaskActivity.class);
            }
        });
    }

}
