package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.Date;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface AddTodoInteractor {

    void addTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, boolean completed);
    void updateTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, String id);
}
