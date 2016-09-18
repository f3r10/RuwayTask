package com.tnb.f3r10.todoapp.todoList.ui;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.List;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface TodoListView {

    void showProgress();
    void hideProgress();
    void showError(String msg);

    void setTodos(List<Todo> data);

}
