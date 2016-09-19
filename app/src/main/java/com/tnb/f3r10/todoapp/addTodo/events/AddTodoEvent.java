package com.tnb.f3r10.todoapp.addTodo.events;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.List;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoEvent {



    private Todo todo;

    boolean error = false;


    boolean isUpdate = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

}
