package com.tnb.f3r10.todoapp.todoList;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface TodoListRepository {

    void removeTodo(Todo todo);
    void getTodos();
}
