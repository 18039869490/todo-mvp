package com.example.yql.todoapp.data.source;

import android.support.annotation.NonNull;


import com.example.yql.todoapp.data.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yql on 2016/5/29.
 */
public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;
    private TasksDataSource mTasksRemoteDataSource = null;

    private TasksDataSource mTasksLocalDataSource = null;


    //--------------Base variable------------------
    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This
     * variable has package local visibility so it can be accessed from tests
     */
    boolean mCacheIsDirty = false;

    /**
     * This variable has package local visibility so it can be accessed from tests
     */
    Map<String, Task> mCacheTasks;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource, @NonNull TasksDataSource tasksLocalDataSource) {
        if(tasksRemoteDataSource != null) {
            mTasksRemoteDataSource = tasksRemoteDataSource;
        }

        if(tasksLocalDataSource != null) {
            mTasksLocalDataSource = tasksLocalDataSource;
        }
    }

    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource, TasksDataSource tasksLocalDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        if(task == null) {
            throw new NullPointerException("task cannot be null");
        }
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);

        mCacheTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    public void getTasks(@NonNull LoadTasksCallback callback) {
        if(callback == null) {
            throw new NullPointerException("callback cannot be null");
        }

        if(mCacheTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(new ArrayList<Task>(mCacheTasks.values()));
            return;
        }
        if(mCacheIsDirty) {
            //If the cache is dirty, we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback);
        }
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadTasksCallback callback) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(new ArrayList<Task>(mCacheTasks.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void refreshCache(List<Task> tasks) {
        if(mCacheTasks == null) {
            mCacheTasks = new LinkedHashMap<>();
        }
        mCacheTasks.clear();
        for(Task task : tasks) {
            mCacheTasks.put(task.getId(), task);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for(Task task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();

        if(mCacheTasks == null) {
            mCacheTasks = new LinkedHashMap<>();
        }
        mCacheTasks.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
