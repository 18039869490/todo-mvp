package com.example.yql.todoapp.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.yql.todoapp.data.Task;
import com.example.yql.todoapp.data.source.TasksDataSource;

/**
 * Listener to user actions from the UI({@link AddEditTaskFragment}), retrieves the
 * data and updates the UI as required
 *
 * Created by yql on 2016/5/31.
 */
public class AddEditTasksPresenter implements AddEditTaskContract.Presenter, TasksDataSource.GetTaskCallback{

    @NonNull
    private final TasksDataSource mTasksRepository;

    @NonNull
    private final AddEditTaskContract.View mAddTaskView;

    @Nullable
    private String mTaskId;


    /**
     * Creates a presenter for the add/edit view.
     *
     * @param mTaskId ID of the task to edit or null for a new task
     * @param mTasksRepository a repository of data for tasks
     * @param mAddTaskView the add/edit view
     */
    public AddEditTasksPresenter(String mTaskId, @NonNull TasksDataSource mTasksRepository, @NonNull AddEditTaskContract.View mAddTaskView) {
        this.mTaskId = mTaskId;
        this.mTasksRepository = mTasksRepository;
        this.mAddTaskView = mAddTaskView;
        mAddTaskView.setPresenter(this);
    }

    @Override
    public void start() {
        if(!isNewTask()) {
            populateTask();
        }
    }

    @Override
    public void saveTask(String title, String description) {
        if(isNewTask()) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public void populateTask() {
        if(isNewTask()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }
        mTasksRepository.getTask(mTaskId, this);
    }

    @Override
    public void onTaskLoaded(Task task) {
        //The view may not be able to handle UI updates anymore
        if(mAddTaskView.isActive()) {
            mAddTaskView.setTitle(task.getTitle());
            mAddTaskView.setDescription(task.getDescription());
        }
    }

    @Override
    public void onDataNotAvailable() {
        //The view may not be able to handle UI updates anymore
        if(mAddTaskView.isActive()) {
            mAddTaskView.showEmptyTaskError();
        }
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    private void createTask(String title, String description) {
        Task newTask = new Task(title, description);
        if(newTask.isEmpty()) {
            mAddTaskView.showEmptyTaskError();
        } else {
            mTasksRepository.saveTask(newTask);
            mAddTaskView.showTaskList();
        }
    }

    private void updateTask(String title, String description) {
        if(isNewTask()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mTasksRepository.saveTask(new Task(title, description, mTaskId));
        //After an edit, go back to the list
        mAddTaskView.showTaskList();
    }
}
