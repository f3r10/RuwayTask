package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.model.Todo;

import java.util.Date;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoInteractorImpl implements AddTodoInteractor {
    private AddTodoRepository repository;

    public AddTodoInteractorImpl() {
        this.repository = new AddTodoRepositoryImpl();
    }



    @Override
    public void addTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, boolean completed) {
        repository.addTodoTask(nameTask, dueDate, taskNotes, priority, completed);
    }

    @Override
    public void updateTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, String id) {
        repository.updateTodoTask(nameTask, dueDate, taskNotes, priority, id);
    }
}
