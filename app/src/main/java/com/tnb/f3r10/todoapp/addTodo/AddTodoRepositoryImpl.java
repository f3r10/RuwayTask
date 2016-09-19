package com.tnb.f3r10.todoapp.addTodo;

import android.util.Log;

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
    public void addTodoTask(final String nameTask, final Date dueDate, final String taskNotes, final int priority, final boolean status) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Todo todo = realm.createObject(Todo.class);
                    String currentId = UUID.randomUUID().toString();
                    todo.setId(currentId);
                    todo.setStatus(status);
                    todo.setTodoName(nameTask);
                    todo.setTodoDate(dueDate);
                    todo.setNotes(taskNotes);
                    todo.setPriority(priority);
                    postSuccess();
                }

            });
    }

    @Override
    public void updateTodoTask(String nameTask, Date dueDate, String taskNotes, int priority, String id) {
        Todo todo = realm.where(Todo.class).equalTo("id", id).findFirst();
        if(todo !=null){
            realm.beginTransaction();
            todo.setPriority(priority);
            todo.setNotes(taskNotes);
            todo.setTodoName(nameTask);
            todo.setTodoDate(dueDate);
            realm.commitTransaction();
            postSuccess(todo);
        }else{
            postError();
        }



    }

    private void postSuccess() {
        post(false, null, false);
    }

    private void postSuccess(Todo todo) {
        post(false, todo, true);
    }

    private void postError(){
        post(true, null, false);
    }


    private void post(boolean error, Todo todo, boolean isUpdate){
        AddTodoEvent event = new AddTodoEvent();
        event.setTodo(todo);
        event.setUpdate(isUpdate);
        event.setError(error);
        eventBus.post(event);
    }
}
