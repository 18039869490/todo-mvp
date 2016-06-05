package com.example.yql.todoapp.tasks;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.yql.todoapp.R;
import com.example.yql.todoapp.data.Task;

import java.util.List;

/**
 * 列表适配器
 * Author：wave
 * Date：2016/5/16
 * Description：
 */
public class TasksAdapter extends BaseAdapter {

    private List<Task> mTasks;
    private TaskItemListener mItemListener;
    private LayoutInflater mInflater = null;

    public TasksAdapter(List<Task> tasks, TaskItemListener itemListener) {
        this.mTasks = tasks;
        this.mItemListener = itemListener;
    }

    public void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<Task> tasks) {
        if(tasks != null) {
            mTasks = tasks;
        }
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;

        if(convertView == null) {
            if(mInflater == null) {
                mInflater = LayoutInflater.from(parent.getContext());
            }
            viewHold = new ViewHold();
            convertView = mInflater.inflate(R.layout.tasks_item, parent, false);
            viewHold.complete = (CheckBox) convertView.findViewById(R.id.complete);
            viewHold.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        //init data
        final Task task = getItem(position);
        viewHold.title.setText(task.getTitleForList());
        viewHold.complete.setChecked(task.isCompleted());

        if(task.isCompleted()) {
            convertView.setBackgroundDrawable(ContextCompat.getDrawable(parent.getContext(), R.drawable.list_completed_touch_feedback));
        }

        //Set up listener
        viewHold.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!task.isCompleted()) {
                    mItemListener.onCompletedTaskClick(task);
                } else {
                    mItemListener.onActivateTaskClick(task);
                }
            }
        });

        return convertView;
    }

    private class ViewHold {
        CheckBox complete;
        TextView title;
    }

    public interface TaskItemListener {
        void onTaskClick(Task clickedTask);
        void onCompletedTaskClick(Task completedTask);
        void onActivateTaskClick(Task activatedTask);
    }
}
