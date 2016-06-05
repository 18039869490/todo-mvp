package com.example.yql.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Immutable model class for a Task
 * Author：wave
 * Date：2016/5/16
 * Description：
 */
public class Task {
    private String mId;
    private String mTitle;
    private String mDescription;
    private boolean mCompleted;

    /**
     * Use this constructs to create a new active Task
     * @param title
     * @param description
     */
    public Task(@Nullable String title, @NonNull String description) {
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mDescription = description;
        mCompleted = false;
    }

    /**
     * Use this construct to create an active Task if the Task already has an id
     * @param title
     * @param description
     * @param id
     */
    public Task(@Nullable String title, @NonNull String description, String id) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = false;
    }

    /**
     * Use this construct to create new completed Task.
     * @param title
     * @param description
     * @param completed
     */
    public Task(@Nullable String title, @NonNull String description, boolean completed) {
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public Task(@NonNull String title, @NonNull String description, String id, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    @Nullable
    public String getTitleForList() {
        if (mTitle != null && !mTitle.equals("")) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isEmpty() {
        return (mTitle == null || "".equals(mTitle)) &&
                (mDescription == null || "".equals(mDescription));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return mId.equals(task.getId()) &&
                mTitle.equals(task.getTitle()) &&
                mDescription.equals(task.getDescription());
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }


}
