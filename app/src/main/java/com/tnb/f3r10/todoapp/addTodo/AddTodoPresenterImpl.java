package com.tnb.f3r10.todoapp.addTodo;

import android.util.Log;

import com.tnb.f3r10.todoapp.addTodo.events.AddTodoEvent;
import com.tnb.f3r10.todoapp.addTodo.ui.AddTodoView;
import com.tnb.f3r10.todoapp.libs.EventBus;
import com.tnb.f3r10.todoapp.libs.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoPresenterImpl implements AddTodoPresenter {

    private EventBus eventBus;
    private AddTodoInteractor interactor;
    private AddTodoView view;

    public AddTodoPresenterImpl(AddTodoView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddTodoInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, boolean completed) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.addTodoTask(nameTask, dueDate, taskNotes, priority, completed);
    }

    @Override
    public void updateTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, String id) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }

        interactor.updateTodoTask(nameTask, dueDate, taskNotes, priority, id);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddTodoEvent event) {
        if(view != null){
            view.hideProgress();
            view.showInput();
        }

        if(event.isError()){
            view.errorAddTodo();
        }else{
            if(event.isUpdate()){
                Log.d("Update", "is updated");
                view.succesUpdateTodo(event.getTodo());
            }else{
                view.succesAddTodo();
            }

        }
    }
}
