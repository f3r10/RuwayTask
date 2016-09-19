package com.tnb.f3r10.todoapp.todoDetail;

/**
 * Created by f3r10 on 18/9/16.
 */
public class TodoDetailInteractorImpl implements TodoDetailInteractor {

    TodoDetailRepository repository;


    public TodoDetailInteractorImpl() {
        this.repository = new TodoDetailRepositoryImpl();
    }



    @Override
    public void deleteTask(String id) {
        repository.deleteTask(id);
    }
}
