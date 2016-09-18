package com.tnb.f3r10.todoapp.todoList;


import com.tnb.f3r10.todoapp.libs.GreenRobotEventBus;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.events.TodoListEvent;



import java.util.ArrayList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by f3r10 on 17/9/16.
 */

public class TodoListRepositoryImpl implements TodoListRepository {

    private GreenRobotEventBus eventBus;
    Realm realm = Realm.getDefaultInstance();

    public TodoListRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
    }



    @Override
    public void removeTodo(Todo todo) {
        realm.beginTransaction();
        todo.deleteFromRealm();
        realm.commitTransaction();
        post("Deleted task", TodoListEvent.DELETE_EVENT);
    }

    @Override

    public void getTodos() {
        RealmQuery<Todo> query = realm.where(Todo.class);
        RealmResults<Todo> todos = query.findAll();
        post(todos);

    }

    private void post(String errorMsg, int errorType){
        TodoListEvent event = new TodoListEvent();
        event.setType(errorType);
        event.setError(errorMsg);
        eventBus.post(event);
    }

    private void post(List<Todo> todos){
        TodoListEvent event = new TodoListEvent();
        event.setType(TodoListEvent.READ_EVENT);
        event.setTodo(todos);
        eventBus.post(event);
    }


}
