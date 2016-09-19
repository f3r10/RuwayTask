package com.tnb.f3r10.todoapp.model;



import org.parceler.Parcel;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.TodoRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by f3r10 on 17/9/16.
 */
@Parcel(implementations = {TodoRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Todo.class})
public class Todo extends RealmObject {

    @PrimaryKey
    private String  id;
    private String todoName;
    private Date todoDate;
    private Boolean status;
    private String notes;
    private int priority;

    public final static int HIGH = 0;
    public final static int MEDIUM = 1;
    public final static int LOW = 2;
    public final static boolean COMPLETED = true;
    public final static boolean TODO_DO = false;


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public Date getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
