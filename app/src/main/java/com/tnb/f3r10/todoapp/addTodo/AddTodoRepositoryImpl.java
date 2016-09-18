package com.tnb.f3r10.todoapp.addTodo;

import com.tnb.f3r10.todoapp.addTodo.events.AddTodoEvent;
import com.tnb.f3r10.todoapp.libs.EventBus;
import com.tnb.f3r10.todoapp.libs.GreenRobotEventBus;
import com.tnb.f3r10.todoapp.model.Todo;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by f3r10 on 17/9/16.
 */
public class AddTodoRepositoryImpl implements AddTodoRepository {
    private EventBus eventBus;
    Realm realm = Realm.getDefaultInstance();
    public AddTodoRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void addTodoTask(final String title, final String id) {
        if( id !=null){
            Todo todo = realm.where(Todo.class).equalTo("id", id).findFirst();
            realm.beginTransaction();
            if (todo != null) {
               todo.setTodoName(title);

            }
            realm.commitTransaction();
            postSuccess();
        }else{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Todo todo = realm.createObject(Todo.class);
                    String currentId = UUID.randomUUID().toString();
                    todo.setId(currentId);
                    todo.setStatus(false);
                    todo.setTodoName(title);
                    todo.setTodoDate(new Date());
                    postSuccess();
                }

            });
        }





    }

    private void postSuccess() {
        post(false);
    }

    private void postError(){
        post(true);
    }


    private void post(boolean error){
        AddTodoEvent event = new AddTodoEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
