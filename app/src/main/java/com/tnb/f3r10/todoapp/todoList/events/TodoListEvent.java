package com.tnb.f3r10.todoapp.todoList.events;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.List;

/**
 * Created by f3r10 on 17/9/16.
 */
public class TodoListEvent {

    private List<Todo> todo;
    private String error;
    private int type;

    public final static int READ_EVENT = 0;
    public final static int UPDATE_EVENT = 1;
    public final static int DELETE_EVENT = 2;
    public final static int ERROR_EVENT = 3;


    public List<Todo> getTodo() {
        return todo;
    }

    public void setTodo(List<Todo> todo) {
        this.todo = todo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
