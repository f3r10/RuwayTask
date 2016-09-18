package com.tnb.f3r10.todoapp.addTodo.events;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.List;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoEvent {

    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
