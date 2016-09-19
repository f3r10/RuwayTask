package com.tnb.f3r10.todoapp.todoList.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tnb.f3r10.todoapp.R;
import com.tnb.f3r10.todoapp.model.Todo;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by f3r10 on 17/9/16.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {


    private List<Todo> todoList;
    private OnItemClickListener onItemClickListener;
    private OnClickListener onClickListener;

    public TodoListAdapter(List<Todo> todoList, OnItemClickListener onItemClickListener, OnClickListener onClickListener) {
        this.todoList = todoList;
        this.onItemClickListener = onItemClickListener;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo item = todoList.get(position);
        holder.setClickListener(item, onItemClickListener);
        holder.myListener(item, onClickListener);
        String name = item.getTodoName();
        Date date = item.getTodoDate();
        Boolean status = item.getStatus();
        String priorityText = "H";
        int colorPriority = Color.RED;
        switch (item.getPriority()){
            case Todo.HIGH:
                colorPriority = Color.RED;
                priorityText = "H";
                break;
            case Todo.MEDIUM:
                colorPriority = Color.GREEN;
                priorityText = "M";
                break;
            case Todo.LOW:
                colorPriority = Color.BLUE;
                priorityText = "L";
        }

        String formatDate = DateFormat.getDateInstance().format(date);

        holder.txtName.setText(name);
        holder.txtDate.setText(formatDate);
        holder.checkboxItem.setChecked(status);
        holder.txtPriority.setText(priorityText);
        holder.txtPriority.setBackgroundColor(colorPriority);


    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void add(Todo todo){
        if(!todoList.contains(todo)){
            todoList.add(todo);
            notifyDataSetChanged();
        }
    }

    public void update(Todo todo){
        if(todoList.contains(todo)){
            int index = todoList.indexOf(todo);
            todoList.set(index, todo);
            notifyDataSetChanged();
        }
    }

    public void remove(Todo todo){
        if(todoList.contains(todo)){
            todoList.remove(todo);
            notifyDataSetChanged();
        }
    }

    public void setTodos(List<Todo> todos){
        this.todoList = todos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.checkboxItem)
        CheckBox checkboxItem;
        @Bind(R.id.txtName)
        TextView txtName;
        @Bind(R.id.txtDate)
        TextView txtDate;
        @Bind(R.id.txtPriority)
        TextView txtPriority;

        private View view ;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        private void myListener(final Todo todo, final OnClickListener listener){
            checkboxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onCheckboxClick(todo, checkboxItem.isChecked());
                }
            });

        }

        private void setClickListener(final Todo todo, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(todo);
                }
            });




            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(todo);
                    return false;
                }
            });
        }
    }
}
