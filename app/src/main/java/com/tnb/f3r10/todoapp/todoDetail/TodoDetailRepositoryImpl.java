package com.tnb.f3r10.todoapp.todoDetail;

import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.events.TodoListEvent;

import io.realm.Realm;

/**
 * Created by f3r10 on 18/9/16.
 */
public class TodoDetailRepositoryImpl implements TodoDetailRepository {

    Realm realm = Realm.getDefaultInstance();

    @Override
    public void deleteTask(String id) {
        Todo todo = realm.where(Todo.class).equalTo("id", id).findFirst();
        if(todo !=null){
            realm.beginTransaction();
            todo.deleteFromRealm();
            realm.commitTransaction();

        }
    }
}
