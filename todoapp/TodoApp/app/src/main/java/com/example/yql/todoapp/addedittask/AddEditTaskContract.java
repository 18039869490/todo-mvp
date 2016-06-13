package com.example.yql.todoapp.addedittask;


import com.example.yql.todoapp.BasePresenter;
import com.example.yql.todoapp.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by yql on 2016/5/31.
 */
public class AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTaskList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();
    }
}
