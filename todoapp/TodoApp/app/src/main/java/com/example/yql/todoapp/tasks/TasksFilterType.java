package com.example.yql.todoapp.tasks;

/**
 * Used with the filter spinner in the tasks list.
 * Author：wave
 * Date：2016/5/28
 * Description：
 */
public enum TasksFilterType {
    /**
     * DO not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active(not completed yet) tasks
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}
