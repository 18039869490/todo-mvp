package com.example.yql.todoapp.tasks;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yql.todoapp.R;
import com.example.yql.todoapp.data.Task;

import java.util.List;

/**
 * Author：wave
 * Date：2016/7/8
 * Description：
 */
public class TasksAdapterR extends RecyclerView.Adapter<TasksAdapterR.MyViewHolder> {

    private List<Task> mTasks;
    private TaskItemListener mItemListener;

    public TasksAdapterR(List<Task> tasks, TaskItemListener itemListener) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tasks_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Task task = mTasks.get(position);
        holder.title.setText(task.getTitleForList());
        holder.complete.setChecked(task.isCompleted());

        if(task.isCompleted()) {
            //convertView.setBackgroundDrawable(ContextCompat.getDrawable(parent.getContext(), R.drawable.list_completed_touch_feedback));
        }

        //Set up listener
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!task.isCompleted()) {
                    mItemListener.onCompletedTaskClick(task);
                } else {
                    mItemListener.onActivateTaskClick(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox complete;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            complete = (CheckBox) itemView.findViewById(R.id.complete);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface TaskItemListener {
        void onTaskClick(Task clickedTask);
        void onCompletedTaskClick(Task completedTask);
        void onActivateTaskClick(Task activatedTask);
    }
}
