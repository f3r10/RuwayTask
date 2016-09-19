package com.tnb.f3r10.todoapp.todoList;

import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.events.TodoListEvent;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface TodoListPresenter {

    void onCreate();
    void onDestroy();

    void onEventMainThread(TodoListEvent event);

    void removedTodo(Todo todo);

    void getTodos();
    void updateStatusTodoTask(String id, boolean status);

}
