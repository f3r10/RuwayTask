package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.addTodo.events.AddTodoEvent;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface AddTodoPresenter {

    void onShow();
    void onDestroy();

    void addTodoTask(String task, String id);
    void onEventMainThread(AddTodoEvent event);
}
