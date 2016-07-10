package com.example.yql.todoapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.example.yql.todoapp.data.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

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
        if(task == null) {
            throw new NullPointerException("completedTask cannot be null");
        }
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);
        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);

        //Do in memory cache update to keep the app UI up to date.
        if(mCacheTasks == null) {
            mCacheTasks = new LinkedHashMap<>();
        }
        mCacheTasks.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        if(TextUtils.isEmpty(taskId)) {
            throw new NullPointerException("taskId cannot be null!");
        }
        completeTask(getTaskWithId(taskId));
    }

    @Override
    public void activateTask(@NonNull Task task) {
        if(task == null) {
            throw new NullPointerException("task cannot be null");
        }
        mTasksRemoteDataSource.activateTask(task);
        mTasksLocalDataSource.activateTask(task);

        Task activeTask = new Task(task.getTitle(), task.getDescription(), task.getId());
        // Do in memory cache update to keep the app UI up to date
        if (mCacheTasks == null) {
            mCacheTasks = new LinkedHashMap<>();
        }
        mCacheTasks.put(task.getId(), activeTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        if(TextUtils.isEmpty(taskId)) {
            throw new NullPointerException("taskId cannot be null!");
        }
        activateTask(getTaskWithId(taskId));
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks();
        mTasksLocalDataSource.clearCompletedTasks();

        // Do in memory cache update to keep the app UI up to date
        if (mCacheTasks == null) {
            mCacheTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Task>> it = mCacheTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
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
    public void getTask(@NonNull final String taskId, @NonNull final GetTaskCallback callback) {
        if(TextUtils.isEmpty(taskId)) {
            throw new NullPointerException("taskId cannot be null");
        }
        if(TextUtils.isEmpty(taskId)) {
            throw new NullPointerException("callback cannot be null");
        }

        //Respond immediately with cache if available
        Task cachedTask = getTaskWithId(taskId);
        if(mCacheTasks != null) {
            callback.onTaskLoaded(cachedTask);
            return;
        }

        //Load from server/persisted if needed

        //Is the task in the local data source? If not, query the network
        mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                mTasksRemoteDataSource.getTask(taskId, new GetTaskCallback() {
                    @Override
                    public void onTaskLoaded(Task task) {
                        callback.onTaskLoaded(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
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
        mTasksRemoteDataSource.deleteTask(checkNotNull(taskId));
        mTasksLocalDataSource.deleteTask(checkNotNull(taskId));

        mCacheTasks.remove(taskId);
    }

    @Override
    public Task getTaskWithId(@NonNull String taskId) {
        if(TextUtils.isEmpty(taskId)) {
            throw new NullPointerException("id cannot be null");
        }

        if(mCacheTasks == null || mCacheTasks.isEmpty()) {
            return null;
        }

        return mCacheTasks.get(taskId);
    }

//    @Nullable
//    private Task getTaskWidthId(@NonNull String id) {
//        if(TextUtils.isEmpty(id)) {
//            throw new NullPointerException("id cannot be null");
//        }
//
//        if(mCacheTasks == null || mCacheTasks.isEmpty()) {
//            return null;
//        }
//
//        return mCacheTasks.get(id);
//    }
}
