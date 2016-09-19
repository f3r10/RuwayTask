package com.tnb.f3r10.todoapp.todoList.ui.adapter;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 18/9/16.
 */
public interface OnClickListener {

    void onCheckboxClick(Todo todo, boolean status);
}
