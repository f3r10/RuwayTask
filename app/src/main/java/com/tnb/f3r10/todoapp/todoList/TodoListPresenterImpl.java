package com.tnb.f3r10.todoapp.todoList;

import com.tnb.f3r10.todoapp.libs.EventBus;
import com.tnb.f3r10.todoapp.libs.GreenRobotEventBus;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.events.TodoListEvent;
import com.tnb.f3r10.todoapp.todoList.ui.TodoListView;

import org.greenrobot.eventbus.Subscribe;


/**
 * Created by f3r10 on 17/9/16.
 */
public class TodoListPresenterImpl implements TodoListPresenter {

    private EventBus eventBus;
    private TodoListView view;
    private TodoListInteractor interactor;

    public TodoListPresenterImpl(TodoListView view) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.view = view;
        this.interactor = new TodoListInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);

    }

    @Override
    @Subscribe
    public void onEventMainThread(TodoListEvent event) {
        if(this.view != null){
            switch (event.getType()){
                case TodoListEvent.READ_EVENT:
                    view.hideProgress();
                    view.setTodos(event.getTodo());
                    break;
                case TodoListEvent.ERROR_EVENT:
                    view.hideProgress();
                    view.showError(event.getError());
                    break;
                case TodoListEvent.DELETE_EVENT:
                    view.hideProgress();
                    view.showError(event.getError());
                    break;
            }
        }
    }

    @Override
    public void removedTodo(Todo todo) {
        if( view != null){
            view.showProgress();
        }
        interactor.removeTodo(todo);
    }



    @Override
    public void getTodos() {
        if( view != null){
            view.showProgress();
        }
        interactor.getTodos();
    }
}
