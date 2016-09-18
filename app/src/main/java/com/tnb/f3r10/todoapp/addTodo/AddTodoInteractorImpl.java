package com.tnb.f3r10.todoapp.addTodo;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoInteractorImpl implements AddTodoInteractor {
    private AddTodoRepository repository;

    public AddTodoInteractorImpl() {
        this.repository = new AddTodoRepositoryImpl();
    }

    @Override
    public void addTodoTask(String title, String id) {
        repository.addTodoTask(title, id);
    }
}
