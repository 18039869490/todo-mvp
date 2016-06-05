package com.example.yql.todoapp.tasks;

import android.support.annotation.NonNull;

import com.example.yql.todoapp.BasePresenter;
import com.example.yql.todoapp.BaseView;
import com.example.yql.todoapp.data.Task;

import java.util.List;

/**
 * Author：wave
 * Date：2016/5/16
 * Description：This specifies the contract between the view and the presenter
 */
public interface TasksContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showTasks(List<Task> tasks);
        void showAddTask();
        void showTaskDetailsUi(String taskId);
        void showTasksMarkedCompleted();
        void showTasksMarkedActive();
        void showLoadingTasksError();
        void showNoTasks();
        void showActiveFilterLabel();
        void showCompletedFilterLabel();
        void showAllFilterLabel();
        void showNoActiveTasks();
        void showNoCompletedTasks();
        void showSuccessFullySaveMessage();
        boolean isActive();
        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestedTask);

        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TasksFilterType requestType);

        TasksFilterType getFiltering();
    }
}
