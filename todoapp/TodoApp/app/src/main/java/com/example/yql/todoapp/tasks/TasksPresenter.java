package com.example.yql.todoapp.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;


import com.example.yql.todoapp.addedittask.AddEditTaskActivity;
import com.example.yql.todoapp.data.Task;
import com.example.yql.todoapp.data.source.TasksDataSource;
import com.example.yql.todoapp.data.source.TasksRepository;
import com.example.yql.todoapp.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：wave
 * Date：2016/5/16
 * Description：Listener to user actions from the UI ({@link TasksFragment}), retrieve the data
 * and update the UI as required
 */
public class TasksPresenter implements TasksContract.Presenter {


    //----------------Base variable----------------
    private TasksRepository mTasksRepository;
    private TasksContract.View mTasksView;
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;
    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View tasksView) {
        if(tasksRepository == null) {
            throw new NullPointerException("tasksRepository cannot be null");
        }
        if(tasksView == null) {
            throw new NullPointerException("tasksView cannot be null");
        }
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;

        //Establish contract between TasksPresenter and View
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks(false);
    }


    @Override
    public void result(int requestCode, int resultCode) {
        //If a task was successfully added, show snackbar
        if(AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        //Simplification for sample. a network reload will be forced on first load.
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }
        if(forceUpdate) {
            //Mark data to dirty
            mTasksRepository.refreshTasks();
        }

        /**
         * The network request might be handled in a different thread so make sure Espresso knows
         * that the app is busy until the response is handle
         */
        EspressoIdlingResource.increment(); //??????????

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<Task>();
                /**
                 * This callback may be called twice, once for the cache and once for loading
                 * the data from the server API, so we check before decrementing, otherwise
                 * it throws "Counter has been corrupted!" exception.
                 */
                if(EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }

                //We filter the tasks based on the requestType
                for(Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;
                        case ACTIVE_TASKS:
                            if(task.isActive()) {
                                tasksToShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if(task.isCompleted()) {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }

                //If the fragment isn`t currently added to its activity, the view may not be able
                //to handle UI updates anymore
                if(!mTasksView.isActive()) {
                    return;
                }
                if(showLoadingUI) {
                    mTasksView.setLoadingIndicator(false);
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processTasks(List<Task> tasks) {
        if(tasks.isEmpty()) {
            //Show a message indicating there are no tasks for that filter type
            processEmptyTasks();
        } else {
            //Show the list of tasks
            mTasksView.showTasks(tasks);
            //Set the filter label`s text
            showFilterLabel();
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be {@link TasksFilterType#ALL_TASKS},
     *                    {@link TasksFilterType#COMPLETED_TASKS}, or
     *                    {@link TasksFilterType#ACTIVE_TASKS}
     */
    @Override
    public void setFiltering(TasksFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mCurrentFiltering;
    }
}
