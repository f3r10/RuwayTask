package com.tnb.f3r10.todoapp.todoList;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface TodoListInteractor {

    void getTodos();
    void removeTodo(Todo todo);

    void updateStatusTodoTask(String id, boolean status);
}
