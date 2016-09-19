package com.tnb.f3r10.todoapp.todoList;


import android.util.Log;

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
        RealmResults<Todo> todos = query.findAll().sort("priority");
        post(todos);

    }

    @Override
    public void updateStatusTodoTask(String id, boolean status) {
        Todo todo = realm.where(Todo.class).equalTo("id", id).findFirst();
        if(todo !=null){
            realm.beginTransaction();
            todo.setStatus(status);
            realm.commitTransaction();
            post(todo);
        }else{
            post("Unable to update status task", TodoListEvent.ERROR_EVENT);
        }
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

    private void post(Todo todo){
        TodoListEvent event = new TodoListEvent();
        event.setType(TodoListEvent.UPDATE_EVENT);
        event.setTodoUpdated(todo);
        eventBus.post(event);
    }


}
