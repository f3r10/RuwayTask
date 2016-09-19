package com.tnb.f3r10.todoapp.todoDetail;

/**
 * Created by f3r10 on 18/9/16.
 */
public class TodoDetailPresenterImpl implements TodoDetailPresenter {

    TodoDetailInteractor interactor;

    public TodoDetailPresenterImpl() {
        interactor = new TodoDetailInteractorImpl();
    }

    @Override
    public void deleteTask(String id) {
        interactor.deleteTask(id);
    }
}
