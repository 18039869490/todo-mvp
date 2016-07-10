package com.example.yql.todoapp.tasks;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
public class TasksAdapterR extends RecyclerView.Adapter<TasksAdapterR.MyViewHolder> implements View.OnClickListener {

    private List<Task> mTasks;
    private TaskItemListener mItemListener = null;
    private Context mContext;

    public TasksAdapterR(List<Task> tasks) {
        this.mTasks = tasks;
    }

    public void setOnItemClickListener(TaskItemListener itemListener) {
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
        mContext = parent.getContext();
        View root = LayoutInflater.from(mContext).inflate(R.layout.tasks_item, parent, false);
        MyViewHolder holder = new MyViewHolder(root);
        //Set up listener
        root.setOnClickListener(this);
        holder.complete.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Task task = mTasks.get(position);
        holder.title.setText(task.getTitleForList());
        holder.complete.setChecked(task.isCompleted());

        if(task.isCompleted()) {
            holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.list_completed_touch_feedback));
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.touch_feedback));
        }

        holder.complete.setTag(task);
        holder.itemView.setTag(task);
    }



    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public void onClick(View v) {
        Task task = null;

        switch (v.getId()) {
            case R.id.complete:
                task = (Task) v.getTag();
                if(mItemListener != null) {
                    if(!task.isCompleted()) {
                        mItemListener.onCompletedTaskClick(task);
                    } else {
                        mItemListener.onActivateTaskClick(task);
                    }
                }
                break;
            default:
                task = (Task) v.getTag();
                mItemListener.onTaskClick(task);
                break;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CheckBox complete;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
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
