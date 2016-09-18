package com.tnb.f3r10.todoapp.todoList.ui.adapter;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface OnItemClickListener {

    void onItemClick(Todo todo);
    void onItemLongClick(Todo todo);
}
