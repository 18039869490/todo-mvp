package com.example.yql.todoapp.taskdetail;

import com.example.yql.todoapp.BasePresenter;
import com.example.yql.todoapp.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by yanqilong on 7/10/16.
 */
public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}
