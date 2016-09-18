package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.addTodo.events.AddTodoEvent;
import com.tnb.f3r10.todoapp.libs.EventBus;
import com.tnb.f3r10.todoapp.libs.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

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
    public void addTodoTask(String task, String id) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.addTodoTask(task, id);
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
            view.succesAddTodo();
        }
    }
}
