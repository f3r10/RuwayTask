package com.tnb.f3r10.todoapp.todoList;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 17/9/16.
 */
public class TodoListInteractorImpl implements TodoListInteractor {

    TodoListRepository repository;

    public TodoListInteractorImpl() {
        this.repository = new TodoListRepositoryImpl();
    }

    @Override
    public void getTodos() {
        repository.getTodos();
    }

    @Override
    public void removeTodo(Todo todo) {
        repository.removeTodo(todo);
    }
}
