package com.tnb.f3r10.todoapp.libs;

/**
 * Created by f3r10 on 17/9/16.
 */
public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
