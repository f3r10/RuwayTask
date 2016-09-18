package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.model.Todo;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface AddTodoView {

    void showProgress();
    void hideProgress();
    void showInput();
    void hideInput();
    void succesAddTodo();
    void errorAddTodo();

}
